package com.exadel.fedorov.repository;

import com.exadel.fedorov.domain.Order;
import com.exadel.fedorov.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Repository
public class OrderRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int create(Order order) {
        return jdbcTemplate.update(
                "INSERT INTO public.orders(status, status_description, time, total_price) VALUES (?, ?, ?, ?);",
                order.getStatus().toString(), order.getStatusDescription(), order.getTime(), order.getTotalPrice());
    }

    public Optional<Order> findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from orders where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Order(
                                rs.getBigDecimal("total_price"),
                                rs.getTimestamp("time"),
                                OrderStatus.valueOf(rs.getString("status")),
                                rs.getString("status_description")

                        ))
        );
    }

    public void update(Order order) {
        jdbcTemplate.update(
                "UPDATE public.orders SET status=?, status_description=?, total_price=? WHERE id = ?;",
                order.getStatus(), order.getStatusDescription(), order.getTotalPrice(), order.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("delete from public.orders where id = ?", id);
    }

    public List<Order> findAll() {
        return jdbcTemplate.query(
                "select * from public.orders",
                (rs, rowNum) ->
                        new Order(
                                rs.getBigDecimal("total_price"),
                                rs.getTimestamp("time"),
                                OrderStatus.valueOf(rs.getString("status")),
                                rs.getString("status_description")
                        ));
    }

}
