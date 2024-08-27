package myApp.service;

import myApp.entity.Client;
import myApp.entity.Order;
import myApp.repository.ClientRepository;
import myApp.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Positive Test: Saving a client successfully
    @Test
    void testSaveClient_Positive() {
        Client client = new Client(1, "John Doe", LocalDateTime.now(), "PENDING");
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        clientService.saveClient(client);

        verify(clientRepository, times(1)).save(client);
    }

    // Negative Test: Saving a null client (should not save)
    @Test
    void testSaveClient_Negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientService.saveClient(null);
        });

        verify(clientRepository, times(0)).save(any(Client.class));
    }

    // Corner Case: Saving a client with minimal details (empty name)
    @Test
    void testSaveClient_CornerCase() {
        Client client = new Client(1, "", LocalDateTime.now(), "PENDING");
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        clientService.saveClient(client);

        verify(clientRepository, times(1)).save(client);
    }

    // Positive Test: Updating client status and creating an order successfully
    @Test
    void testUpdateClientStatusAndCreateOrder_Positive() {
        clientService.setUpdateClientAndCreateOrderEnabled(true);
        Client client = new Client(2, "Jane Doe", LocalDateTime.now(), "PENDING");
        when(clientRepository.findById(2)).thenReturn(Optional.of(client));

        clientService.updateClientStatusAndCreateOrder(2, "ACTIVATED", 2, 200.65);

        verify(clientRepository, times(1)).save(client);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // Negative Test: Client not found for updating status
    @Test
    void testUpdateClientStatusAndCreateOrder_Negative() {
        clientService.setUpdateClientAndCreateOrderEnabled(true);
        when(clientRepository.findById(3)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            clientService.updateClientStatusAndCreateOrder(3, "ACTIVATED", 3, 300.75);
        });

        verify(clientRepository, times(0)).save(any(Client.class));
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    // Corner Case: Update status to a client with an empty status and create order
    @Test
    void testUpdateClientStatusAndCreateOrder_CornerCase() {
        clientService.setUpdateClientAndCreateOrderEnabled(true);
        Client client = new Client(4, "Empty Status Client", LocalDateTime.now(), "");
        when(clientRepository.findById(4)).thenReturn(Optional.of(client));

        clientService.updateClientStatusAndCreateOrder(4, "ACTIVATED", 4, 100.00);

        verify(clientRepository, times(1)).save(client);
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
