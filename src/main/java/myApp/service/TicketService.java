package myApp.service;

import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void saveTicket(Ticket ticket) {
        ticketDAO.saveTicket(ticket);
    }
}