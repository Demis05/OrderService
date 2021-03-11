package com.exadel.fedorov.repository;

import com.exadel.fedorov.domain.Order;
import com.exadel.fedorov.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class OrderRepository {

    private static final String CREATE_QUERY = "INSERT INTO public.orders(status, status_description, time, total_price) VALUES (?, ?, ?, ?);";
    private static final String FIND_BY_ID_QUERY = "select * from orders where id = ?";
    private static final String UPDATE_QUERY = "UPDATE public.orders SET status=?, status_description=?, total_price=? WHERE id = ?;";
    private static final String FIND_ALL_QUERY = "select * from public.orders";
    private static final String DELETE_QUERY = "delete from public.orders where id = ?";
    private static final String TOTAL_PRICE_FIELD = "total_price";
    private static final String TIME_FIELD = "time";
    private static final String STATUS_FIELD = "status";
    private static final String STATUS_DESCRIPTION_FIELD = "status_description";
    private static final String ID_FIELD = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcCall = new SimpleJdbcCall(dataSource);
    }

    public int create(Order order) {
        return jdbcTemplate.update(
                CREATE_QUERY,
                order.getStatus().toString(), order.getStatusDescription(), order.getTime(), order.getTotalPrice());
    }

    public Optional<Order> findById(Long id) {
        return jdbcTemplate.queryForObject(
                FIND_BY_ID_QUERY,
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Order(
                                rs.getBigDecimal(TOTAL_PRICE_FIELD),
                                rs.getTimestamp(TIME_FIELD),
                                OrderStatus.valueOf(rs.getString(STATUS_FIELD)),
                                rs.getString(STATUS_DESCRIPTION_FIELD)
                        ))
        );
    }

    public void update(Order order) {
        jdbcTemplate.update(
                UPDATE_QUERY,
                order.getStatus(), order.getStatusDescription(), order.getTotalPrice(), order.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public List<Order> findAll() {
        return jdbcTemplate.query(
                FIND_ALL_QUERY,
                (rs, rowNum) ->
                        new Order(
                                rs.getBigDecimal(TOTAL_PRICE_FIELD),
                                rs.getTimestamp(TIME_FIELD),
                                OrderStatus.valueOf(rs.getString(STATUS_FIELD)),
                                rs.getString(STATUS_DESCRIPTION_FIELD)
                        ));
    }

    public Order findByIdWithStoredProcedure(Long id) {
        simpleJdbcCall.withProcedureName("READ_EMPLOYEE");
        SqlParameterSource in = new MapSqlParameterSource().addValue(ID_FIELD, id);
        Map<String, Object> out = simpleJdbcCall.execute(in);

        Order order = new Order();
        order.setTotalPrice((BigDecimal) out.get(TOTAL_PRICE_FIELD));
        order.setTime((Timestamp) out.get(TIME_FIELD));
        order.setStatus(OrderStatus.valueOf((String) out.get(STATUS_FIELD)));
        order.setStatusDescription((String) out.get(STATUS_DESCRIPTION_FIELD));
        return order;
    }

}
