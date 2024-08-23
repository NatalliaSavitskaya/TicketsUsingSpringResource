package myApp.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import static myApp.service.SqlQueries.GET_ORDER_BY_ID;
import static myApp.service.SqlQueries.SAVE_ORDER;

@Repository
public class OrderDAO {
    private final JdbcTemplate jdbcTemplate;

    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveOrder(Order order) {
        jdbcTemplate.update(SAVE_ORDER, order.getId(), order.getClientId(), order.getCreationDate(), order.getSum());
    }

    @Transactional
    public Order getOrder(int orderId) {
        return jdbcTemplate.queryForObject(GET_ORDER_BY_ID, new TicketRowMapper(), orderId);
    }

    private static class TicketRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setClientId(rs.getInt("client_id"));
            order.setSum(rs.getDouble("total_sum"));
            order.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
            return order;
        }
    }
}