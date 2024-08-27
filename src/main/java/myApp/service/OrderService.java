package myApp.service;

import myApp.entity.Order;
import myApp.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrder(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> getOrdersByClientId(int clientId) {
        return orderRepository.findByClientId(clientId);
    }
}
