package com.exadel.fedorov.orders.repository;

import com.exadel.fedorov.orders.domain.Order;
import com.exadel.fedorov.orders.domain.OrderStatus;
import com.exadel.fedorov.orders.dto.dto_request.ReqOrderItemDTO;
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
public class OrderDAO {

    private static final String CREATE_QUERY = "INSERT INTO public.orders(status, status_description, time, total_price) VALUES (?, ?, ?, ?);";
    private static final String FIND_BY_ID_QUERY = "select * from orders where id = ?";
    private static final String UPDATE_QUERY = "UPDATE public.orders SET status=?, status_description=?, total_price=? WHERE id = ?;";
    private static final String FIND_ALL_QUERY = "select * from public.orders";
    private static final String DELETE_QUERY = "delete from public.orders where id = ?";

    private static final String TOTAL_PRICE_FIELD = "total_price";
    private static final String TIME_FIELD = "time";
    private static final String STATUS_FIELD = "status";
    private static final String STATUS_DESCRIPTION_FIELD = "status_description";
    private static final String CLIENT_NAME_FIELD = "client_name";
    private static final String ID_FIELD = "id";
    private static final String FIND_BY_ID_PROCEDURE = "FIND_BY_ID_PROCEDURE";


    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcCall = new SimpleJdbcCall(dataSource);
    }

    public int create(Order order) {  //try to return timestamp
        return jdbcTemplate.update(
                CREATE_QUERY,
                order.getStatus().toString(), order.getStatusDescription(), order.getTime(), order.getTotalPrice());
    }

    public Optional<Order> findById(Long id) {

        /*return jdbcTemplate.queryForObject(
                FIND_BY_ID_QUERY,
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Order(rs.getTimestamp(TIME_FIELD),
                                OrderStatus.valueOf(rs.getString(STATUS_FIELD)),
                                rs.getString(CLIENT_NAME_FIELD),
                                rs.getBigDecimal(TOTAL_PRICE_FIELD),
                                rs.getString(STATUS_DESCRIPTION_FIELD)
                        ))
        );*/
        return null;

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

        /*return jdbcTemplate.query(
                FIND_ALL_QUERY,
                (rs, rowNum) ->
                        new Order(rs.getTimestamp(TIME_FIELD),
                                OrderStatus.valueOf(rs.getString(STATUS_FIELD)),
                                rs.getString(CLIENT_NAME_FIELD),
                                rs.getBigDecimal(TOTAL_PRICE_FIELD),
                                rs.getString(STATUS_DESCRIPTION_FIELD)
                        ));*/
        return null;
    }

    public Order findByIdWithStoredProcedure(Long id) {

        simpleJdbcCall.withProcedureName(FIND_BY_ID_PROCEDURE);

        SqlParameterSource in = new MapSqlParameterSource().addValue(ID_FIELD, id);
        Map<String, Object> out = simpleJdbcCall.execute(in);

    /*    return new Order((Timestamp) out.get(TIME_FIELD),
                OrderStatus.valueOf((String) out.get(STATUS_FIELD)),
                (String) out.get(CLIENT_NAME_FIELD),
                new BigDecimal((int) out.get(TOTAL_PRICE_FIELD)),
                (String) out.get(STATUS_DESCRIPTION_FIELD));*/

        return null;

        //returning dataset with orderId, list<OrderItem>
    }

    public void createOrderWithProcedure(Order order, List<ReqOrderItemDTO> orderItems) {
//
//        simpleJdbcCall.withProcedureName(FIND_BY_ID_PROCEDURE);
//simpleJdbcCall.
//        SqlParameterSource in = new MapSqlParameterSource().addValue(ID_FIELD, id);
//        Map<String, Object> out = simpleJdbcCall.execute(in);

    /*    return new Order((Timestamp) out.get(TIME_FIELD),
                OrderStatus.valueOf((String) out.get(STATUS_FIELD)),
                (String) out.get(CLIENT_NAME_FIELD),
                new BigDecimal((int) out.get(TOTAL_PRICE_FIELD)),
                (String) out.get(STATUS_DESCRIPTION_FIELD));*/



    }
}
