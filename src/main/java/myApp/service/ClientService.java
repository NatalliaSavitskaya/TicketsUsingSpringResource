package myApp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ClientService {

    private final ClientDAO clientDAO;

    @Value("${feature.updateClientAndCreateOrder:true}")
    private boolean updateClientAndCreateOrderEnabled;

    public ClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public void saveClient(Client client) {
        clientDAO.saveClient(client);
    }

    public void updateClientStatusAndCreateOrder(int userId, String newStatus, double sum) {
        if (!updateClientAndCreateOrderEnabled) {
            throw new UnsupportedOperationException("Updating client status and creating a order is disabled.");
        }
        clientDAO.updateClientStatusAndCreateOrder(userId, newStatus, sum);
    }
}