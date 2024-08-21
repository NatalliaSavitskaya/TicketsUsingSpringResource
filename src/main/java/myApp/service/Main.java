package myApp.service;

import java.util.List;

import static myApp.service.BusTicket.getTicketsFromFile;

public class Main {

    public static void main(String[] args) {

        List<BusTicket> loadedTickets = getTicketsFromFile();
        if (loadedTickets != null) {
            System.out.println("All the JSON bus tickets from the file are loaded to the arraylist of bus tickets.");
            for (BusTicket busTicket : loadedTickets) {
                System.out.println(busTicket);
            }
        } else
            System.out.println("There are no tickets in the file.");

    }
}