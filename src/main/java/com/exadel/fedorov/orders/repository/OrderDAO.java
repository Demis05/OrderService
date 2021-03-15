package com.exadel.fedorov.orders.repository;

import com.exadel.fedorov.orders.domain.Order;
import com.exadel.fedorov.orders.dto.dto_request.ReqOrderItemDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class OrderDAO {

    private static final String CREATE_QUERY = "INSERT INTO public.orders(status, status_description, time, total_price) VALUES (?, ?, ?, ?);";
    private static final String FIND_BY_ID_QUERY = "select * from orders where id = ?";
    private static final String UPDATE_QUERY = "UPDATE public.orders SET status=?, status_description=?, total_price=? WHERE id = ?;";
    private static final String FIND_ALL_QUERY = "select * from public.orders";
    private static final String DELETE_QUERY = "delete from public.orders where id = ?";


    private static final String TIME_FIELD = "time";
    private static final String STATUS_FIELD = "status";

    private static final String STATUS_DESCRIPTION_IN_FIELD = "status_description_in";
    private static final String CLIENT_NAME_IN_FIELD = "client_name_in";
    private static final String TOTAL_PRICE_IN_FIELD = "total_price_in";
    private static final String IN_ARRAY_FIELD = "in_array";


    private static final String STATUS_DESCRIPTION_FIELD = "status_description";
    private static final String CLIENT_NAME_FIELD = "client_name";
    private static final String TOTAL_PRICE_FIELD = "total_price";


    private static final String ID_FIELD = "id";
    private static final String CREATE_ORDER_PROCEDURE = "create_order_procedure";
    private static final String PRODUCT_ID_FIELD = "product_id";
    private static final String PRODUCT_COUNT_FIELD = "product_count";
    private static final String POSITION_PRICE_FIELD = "position_price";
    private static final String JSONB_TYPE = "jsonb";


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
                order.getStatus().toString(),
                order.getStatusDescription(),
                order.getTime(),
                order.getTotalPrice());
    }

    public Optional<Order> findById(Long id) {

        return jdbcTemplate.queryForObject(
                FIND_BY_ID_QUERY,
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Order(
                                rs.getString(CLIENT_NAME_FIELD),
                                rs.getBigDecimal(TOTAL_PRICE_FIELD),
                                rs.getString(STATUS_DESCRIPTION_IN_FIELD)
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
                                rs.getString(CLIENT_NAME_FIELD),
                                rs.getBigDecimal(TOTAL_PRICE_FIELD),
                                rs.getString(STATUS_DESCRIPTION_FIELD)
                        )
        );
    }

    /*
   status_description_in text,
   client_name_in text,
   total_price_in money,
   in_array jsonb
   */
    public void createOrderWithProcedure(Order order, List<ReqOrderItemDTO> orderItems) {

        simpleJdbcCall = simpleJdbcCall.withProcedureName(CREATE_ORDER_PROCEDURE);
        JSONArray inArray = new JSONArray();
        final PGobject jsonbObject = new PGobject();

        for (ReqOrderItemDTO item : orderItems) {
            JSONObject jsonItem = new JSONObject();
            jsonItem.put(PRODUCT_ID_FIELD, item.getProductId());
            jsonItem.put(PRODUCT_COUNT_FIELD, item.getCount());
            jsonItem.put(POSITION_PRICE_FIELD, item.getPrice());
            inArray.put(jsonItem);
        }
        try {
            jsonbObject.setType(JSONB_TYPE);
            jsonbObject.setValue(inArray.toString());
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue(STATUS_DESCRIPTION_IN_FIELD, order.getStatusDescription())
                .addValue(CLIENT_NAME_IN_FIELD, order.getClientName())
                .addValue(TOTAL_PRICE_IN_FIELD, order.getTotalPrice())
                .addValue(IN_ARRAY_FIELD, jsonbObject);
        simpleJdbcCall.execute(in);

    }
}
