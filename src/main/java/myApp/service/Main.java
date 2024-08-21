package myApp.service;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
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