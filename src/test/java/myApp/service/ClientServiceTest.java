package myApp.service;

import myApp.entity.Client;
import myApp.entity.Order;
import myApp.repository.ClientRepository;
import myApp.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Collections;

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

    @Captor
    private ArgumentCaptor<Client> clientCaptor;

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {"John Doe", ""})
    void testSaveClient(String name) {
        Client client = new Client(1, name, LocalDateTime.now(), "PENDING");
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        clientService.saveClient(client);

        verify(clientRepository, times(1)).save(clientCaptor.capture());
        assertEquals(client, clientCaptor.getValue());
    }

    @Test
    void testSaveClient_NullClient() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientService.saveClient(null);
        });

        verify(clientRepository, times(0)).save(any(Client.class));
    }

    @Test
    void testSaveClient_ClientNameIsEmpty() {
        Client client = new Client(1, "", LocalDateTime.now(), "PENDING");
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        clientService.saveClient(client);

        verify(clientRepository, times(1)).save(clientCaptor.capture());
        assertEquals(client, clientCaptor.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"PENDING", "ACTIVE"})
    void testUpdateClientStatusAndCreateOrder_Positive(String newStatus) {
        clientService.setUpdateClientAndCreateOrderEnabled(true);
        Client client = new Client(2, "Jane Doe", LocalDateTime.now(), "PENDING");
        when(clientRepository.findById(2)).thenReturn(Optional.of(client));

        clientService.updateClientStatusAndCreateOrder(2, newStatus, 2, 200.65);

        verify(clientRepository, times(1)).save(clientCaptor.capture());
        verify(orderRepository, times(1)).save(orderCaptor.capture());

        assertEquals(newStatus, clientCaptor.getValue().getStatus());
        assertEquals(2, orderCaptor.getValue().getId());
        assertEquals(2, orderCaptor.getValue().getClient().getId());
        assertEquals(200.65, orderCaptor.getValue().getSum());
    }

    @Test
    void testUpdateClientStatusAndCreateOrder_ClientNotFound() {
        clientService.setUpdateClientAndCreateOrderEnabled(true);
        when(clientRepository.findById(3)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            clientService.updateClientStatusAndCreateOrder(3, "ACTIVATED", 3, 300.75);
        });

        verify(clientRepository, times(0)).save(any(Client.class));
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testUpdateClientStatusAndCreateOrder_ClientStatusIsEmpty() {
        clientService.setUpdateClientAndCreateOrderEnabled(true);
        Client client = new Client(4, "Empty Status Client", LocalDateTime.now(), "");
        when(clientRepository.findById(4)).thenReturn(Optional.of(client));

        clientService.updateClientStatusAndCreateOrder(4, "ACTIVATED", 4, 100.00);

        verify(clientRepository, times(1)).save(clientCaptor.capture());
        verify(orderRepository, times(1)).save(orderCaptor.capture());

        assertEquals("ACTIVATED", clientCaptor.getValue().getStatus());
        assertEquals(4, orderCaptor.getValue().getId());
        assertEquals(4, orderCaptor.getValue().getClient().getId());
        assertEquals(100.00, orderCaptor.getValue().getSum());
    }

    @Test
    void testGetClient_Positive() {
        Client expectedClient = new Client(1, "John Doe", LocalDateTime.now(), "ACTIVE");
        when(clientRepository.findById(1)).thenReturn(Optional.of(expectedClient));

        Client actualClient = clientService.getClient(1);

        assertNotNull(actualClient);
        assertEquals(expectedClient, actualClient);
        verify(clientRepository, times(1)).findById(1);
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 0})
    void testGetClient_ClientNotFound(int clientId) {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        Client actualClient = clientService.getClient(clientId);

        assertNull(actualClient);
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void testGetClient_ClientIdIs0() {
        when(clientRepository.findById(0)).thenReturn(Optional.empty());

        Client actualClient = clientService.getClient(0);

        assertNull(actualClient);
        verify(clientRepository, times(1)).findById(0);
    }

    @Test
    void testDeleteClientById_Positive() {
        Client client = new Client(1, "John Doe", LocalDateTime.now(), "ACTIVE");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(orderRepository.findByClientId(1)).thenReturn(Collections.emptyList());

        clientService.deleteClientById(1);

        verify(clientRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteClientById_ClientNotFound() {
        when(clientRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            clientService.deleteClientById(999);
        });

        verify(clientRepository, times(0)).deleteById(anyInt());
    }

    @Test
    void testDeleteClientById_ClientHasOrders() {
        Client client = new Client(2, "Jane Doe", LocalDateTime.now(), "ACTIVE");
        Order order = new Order(1, 2, LocalDateTime.now(), 100.0);
        when(clientRepository.findById(2)).thenReturn(Optional.of(client));
        when(orderRepository.findByClientId(2)).thenReturn(Collections.singletonList(order));

        assertThrows(UnsupportedOperationException.class, () -> {
            clientService.deleteClientById(2);
        });

        verify(clientRepository, times(0)).deleteById(2);
    }
}