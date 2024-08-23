package myApp.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext  context = SpringApplication.run(Main.class, args);

        ClientService clientService = context.getBean(ClientService.class);
        OrderService orderService = context.getBean(OrderService.class);

        try {
            // Example saving User and Ticket to database
            Client client = new Client(1, "New User", LocalDateTime.now(), "PENDING");
            clientService.saveClient(client);
            System.out.println("Client saved successfully: " + client);
            Order order = new Order(1, 1, LocalDateTime.now(), 155.65);
            orderService.saveOrder(order);
            System.out.println("Order saved successfully: " + order);

            // Example updating status of User and creating his/her Ticket
            clientService.saveClient(new Client(2, "Second User", LocalDateTime.now(), "PENDING"));
            clientService.updateClientStatusAndCreateOrder(2, "ACTIVATED", 200.65);
            System.out.println("Client status updated to ACTIVATED and order is created successfully.");
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
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