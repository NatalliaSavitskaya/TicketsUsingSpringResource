package myApp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class ClientDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveClient(Client client) {
        if (entityManager.contains(client)) {
            entityManager.persist(client);
        } else {
            entityManager.merge(client);
        }
    }

    @Transactional(readOnly = true)
    public Client getClient(int id) {
        return entityManager.find(Client.class, id);
    }

    @Transactional
    public void updateClientStatusAndCreateOrder(int clientId, String newStatus, int orderId, double sum) {
        Client client = entityManager.find(Client.class, clientId);
        if (client != null) {
            client.setStatus(newStatus);
            entityManager.merge(client);
            Order order = new Order();
            order.setId(orderId);
            order.setClient(client);
            order.setClientId(clientId);
            order.setCreationDate(LocalDateTime.now());
            order.setSum(sum);
            entityManager.persist(order);
        } else {
            throw new IllegalArgumentException("Client not found with ID: " + clientId);
        }
    }

}
