package myApp;

import myApp.entity.BusTicket;
import myApp.entity.Client;
import myApp.entity.Order;
import myApp.service.BusTicketService;
import myApp.service.ClientService;
import myApp.service.OrderService;
import myApp.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private final SomeService someService;
    private final ClientService clientService;
    private final OrderService orderService;
    private final BusTicketService busTicketService;

    @Autowired
    public AppStartupRunner(SomeService someService, ClientService clientService, OrderService orderService,
                            BusTicketService busTicketService) {
        this.someService = someService;
        this.clientService = clientService;
        this.orderService = orderService;
        this.busTicketService = busTicketService;
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            someService.execute();
        } catch (Exception e) {
            System.out.println("ThisIsFirstConditionalBean is not available in the application context.");
        }

        clientService.saveClient(new Client(1, "Mark Antony", LocalDateTime.now(), "PENDING"));
        Client firstClient = clientService.getClient(1);
        System.out.println("Client saved successfully: " + firstClient);

        Order firstOrder = new Order(1, 1, LocalDateTime.now(), 155.65);
        orderService.saveOrder(firstOrder);
        Order savedOrder = orderService.getOrder(1);
        System.out.println("Order saved successfully: " + savedOrder);

        clientService.deleteClientById(1);

        clientService.saveClient(new Client(2, "Prince BlueRay", LocalDateTime.now(), "PENDING"));
        Client secondClient = clientService.getClient(2);
        System.out.println("Client saved successfully: " + secondClient);
        clientService.updateClientStatusAndCreateOrder(2, "ACTIVATED", 2, 200.65);
        Order createdOrder = orderService.getOrder(2);
        System.out.println("Client status updated to ACTIVATED: " + clientService.getClient(2) +
                " and order is created successfully: " + createdOrder);

        int clientId = 1;
        Order secondOrder = new Order(3, clientId, LocalDateTime.now(), 100.21);
        orderService.saveOrder(secondOrder);
        List<Order> orders = orderService.getOrdersByClientId(clientId);

        System.out.println("Orders for client ID " + clientId + ":");
        for (Order order : orders) {
            System.out.println(order);
        }

        List<BusTicket> loadedTickets = busTicketService.loadBusTickets();
        if (loadedTickets != null && !loadedTickets.isEmpty()) {
            System.out.println(loadedTickets.size() + " lines from the JSON file were loaded to the arraylist of bus tickets.");
            for (BusTicket busTicket : loadedTickets) {
                System.out.println(busTicket);
            }
        } else {
            System.out.println("There are no tickets in the file.");
        }
    }
}