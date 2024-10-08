package myApp.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Resource resource = context.getResource("classpath:application.properties");
        try {
            resource.getInputStream();
            ClientService clientService = context.getBean(ClientService.class);
            OrderService orderService = context.getBean(OrderService.class);
            try {
                // Example saving Client and Order to the database
                clientService.saveClient(new Client(1, "Mark Antony", LocalDateTime.now(), "PENDING"));
                Client firstClient = clientService.getClient(1);
                System.out.println("Client saved successfully: " + firstClient);
                Order order = new Order(1, 1, LocalDateTime.now(), 155.65);
                orderService.saveOrder(order);
                Order savedOrder = orderService.getOrder(1);
                System.out.println("Order saved successfully: " + savedOrder);

                // Example updating status of Client and creating his/her Order
                clientService.saveClient(new Client(2, "Prince BlueRay", LocalDateTime.now(), "PENDING"));
                Client secondClient = clientService.getClient(2);
                System.out.println("Client saved successfully: " + secondClient);
                clientService.updateClientStatusAndCreateOrder(2, "ACTIVATED", 2, 200.65);
                Order createdOrder = orderService.getOrder(2);
                System.out.println("Client status updated to ACTIVATED: " + clientService.getClient(2) +
                        " and order is created successfully: " + createdOrder);
            } catch (UnsupportedOperationException e) {
                System.err.println(e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Loading JSON lines from the file
        BusTicketService busTicketService = context.getBean(BusTicketService.class);

        List<BusTicket> loadedTickets = busTicketService.loadBusTickets();
        if (loadedTickets != null && !loadedTickets.isEmpty()) {
            System.out.println(loadedTickets.size() + " lines from the JSON file were loaded to the arraylist of bus tickets.");
            for (BusTicket busTicket : loadedTickets) {
                System.out.println(busTicket);
            }
        } else
            System.out.println("There are no tickets in the file.");
    }
}