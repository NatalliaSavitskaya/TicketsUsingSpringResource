package myApp.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    public void updateUserStatusAndCreateTicket(int userId, String newStatus, String ticketType) {
        userDAO.updateUserStatusAndCreateTicket(userId, newStatus, ticketType);
    }
}