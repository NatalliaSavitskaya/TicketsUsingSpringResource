package myApp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        // To get the order with id=1 after launching the application:
        // call http://localhost:8080/api/orders/1 in web browser or
        // execute curl -i http://localhost:8080/api/orders/1 in command line or
        // send GET http://localhost:8080/api/orders/1 in Postman
    }
}
