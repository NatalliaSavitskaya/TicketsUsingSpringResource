package myApp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveUser(User user) {
        if (entityManager.contains(user)) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }
    @Transactional
    public void updateUserStatusAndCreateTicket(int userId, String newStatus, String ticketType) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            user.setStatus(newStatus);
            entityManager.merge(user);
            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setUserId(userId);
            ticket.setCreationDate(LocalDateTime.now());
            ticket.setTicketType(ticketType);
            entityManager.persist(ticket);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

}