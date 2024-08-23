package myApp.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.time.LocalDateTime;

import java.sql.SQLException;

import static myApp.service.SqlQueries.*;

@Repository
public class ClientDAO {
    private final JdbcTemplate jdbcTemplate;

    public ClientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveClient(Client client) {
        jdbcTemplate.update(SAVE_CLIENT, client.getId(), client.getName(), client.getCreationDate(), client.getStatus());
    }

    @Transactional
    public Client getClient(int id) {
        return jdbcTemplate.queryForObject(GET_CLIENT_BY_ID, new UserRowMapper(), id);
    }

    @Transactional
    public void updateClientStatusAndCreateOrder(int clientId, String newStatus, int orderId, double sum) {
        Client client = getClient(clientId);
        if (client != null) {
            jdbcTemplate.update(UPDATE_CLIENT_STATUS, newStatus, clientId);
            Order order = new Order(orderId, clientId, LocalDateTime.now(), sum);
            jdbcTemplate.update(SAVE_ORDER, order.getId(), order.getClientId(), order.getCreationDate(), order.getSum());
        } else {
            throw new IllegalArgumentException("Client not found with ID: " + clientId);
        }
    }

    private static class UserRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setName(rs.getString("name"));
            client.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
            client.setStatus(rs.getString("status"));
            return client;
        }
    }
}