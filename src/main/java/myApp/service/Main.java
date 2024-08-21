package myApp.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        TicketService ticketService = context.getBean(TicketService.class);

        // Example saving User and Ticket to database
        User user = new User(1, "New User", LocalDateTime.now(), "PENDING");
        userService.saveUser(user);
        System.out.println("User saved successfully: " + user);
        Ticket ticket = new Ticket(1, 1, LocalDateTime.now(), "DAY");
        ticketService.saveTicket(ticket);
        System.out.println("Ticket saved successfully: " + ticket);

        // Example updating status of User and creating his/her Ticket
        userService.saveUser(new User(2, "Second User", LocalDateTime.now(), "PENDING"));
        userService.updateUserStatusAndCreateTicket(2, "ACTIVATED", "DAY");
        System.out.println("User status updated to ACTIVATED and ticket created successfully.");


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