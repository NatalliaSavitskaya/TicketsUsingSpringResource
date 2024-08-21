package myApp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Value("${feature.updateUserAndCreateTicket:true}")
    private boolean updateUserAndCreateTicketEnabled;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    public void updateUserStatusAndCreateTicket(int userId, String newStatus, String ticketType) {
        if (!updateUserAndCreateTicketEnabled) {
            throw new UnsupportedOperationException("Updating user status and creating a ticket is disabled.");
        }
        userDAO.updateUserStatusAndCreateTicket(userId, newStatus, ticketType);
    }
}