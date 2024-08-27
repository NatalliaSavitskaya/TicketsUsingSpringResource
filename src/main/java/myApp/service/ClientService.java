package myApp.service;

import myApp.entity.Client;
import myApp.entity.Order;
import myApp.repository.ClientRepository;
import myApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    @Value("${feature.updateClientAndCreateOrder:true}")
    private boolean updateClientAndCreateOrderEnabled;

    public ClientService(ClientRepository clientRepository, OrderRepository orderRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    public void saveClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        clientRepository.save(client);
    }

    public Client getClient(int clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    public void updateClientStatusAndCreateOrder(int clientId, String newStatus, int orderID, double sum) {
        if (!updateClientAndCreateOrderEnabled) {
            throw new UnsupportedOperationException("Updating client status and creating an order is disabled.");
        }

        Client client = clientRepository.findById(clientId).orElseThrow(() ->
                new IllegalArgumentException("Client not found with ID: " + clientId)
        );

        client.setStatus(newStatus);
        clientRepository.save(client);

        Order order = new Order();
        order.setId(orderID);
        order.setClient(client);
        order.setClientId(clientId);
        order.setCreationDate(LocalDateTime.now());
        order.setSum(sum);

        orderRepository.save(order);
    }

    public void setUpdateClientAndCreateOrderEnabled(boolean enabled) {
        this.updateClientAndCreateOrderEnabled = enabled;
    }
}