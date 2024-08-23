package myApp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveOrder(Order order) {
        if (entityManager.contains(order)) {
            entityManager.persist(order);
        } else {
            entityManager.merge(order);
        }
    }

    @Transactional(readOnly = true)
    public Order getOrder(int orderId) {
        return entityManager.find(Order.class, orderId);
    }
}