package myApp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ClientService {

    private final ClientDAO clientDAO;

    @Value("${feature.updateClientAndCreateOrder:true}") // the value should be changed to "false"
    // if editing the status of client is forbidden
    private boolean updateClientAndCreateOrderEnabled;

    public ClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public void saveClient(Client client) {
        clientDAO.saveClient(client);
    }

    public Client getClient(int clientId) {
        return clientDAO.getClient(clientId);
    }

    public void updateClientStatusAndCreateOrder(int clientId, String newStatus, int orderID, double sum) {
        if (!updateClientAndCreateOrderEnabled) {
            throw new UnsupportedOperationException("Updating client status and creating a order is disabled.");
        }
        clientDAO.updateClientStatusAndCreateOrder(clientId, newStatus, orderID, sum);
    }
}