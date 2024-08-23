package myApp.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void saveOrder(Order order) {
        orderDAO.saveOrder(order);
    }

    public Order getOrder(int orderId) {
        return orderDAO.getOrder(orderId);
    }
}
