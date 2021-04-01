package com.exadel.fedorov.orders.repository;

import com.exadel.fedorov.orders.domain.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MembershipDAO {

    public static final String UPDATE_MEMBERSHIP_QUERY = "UPDATE memberships SET end_date=? WHERE id=?;";
    public static final String DELETE_MEMBERSHIP_QUERY = "DELETE FROM memberships WHERE id=?;";
    public static final String FIND_CLIENT_MEMBERSHIPS_QUERY = "select * from memberships where client_id=%s";
    public static final String CREATE_MEMBERSHIP_QUERY = "INSERT INTO memberships(id, client_id, title, start_date, end_date, validity, discount) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_CURRENT_MEMBERSHIP_QUERY = "select * from memberships where client_id=? and memberships.end_date>CURRENT_TIMESTAMP;";

    public static final String ID_FIELD = "id";
    public static final String TITLE_FIELD = "title";
    public static final String START_DATE_FIELD = "start_date";
    public static final String END_DATE_FIELD = "end_date";
    public static final String DISCOUNT_FIELD = "discount";
    public static final String VALIDITY_FIELD = "validity";
    public static final String CLIENT_ID_FIELD = "client_id";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(Membership membership) {
        jdbcTemplate.update(
                CREATE_MEMBERSHIP_QUERY,
                membership.getClientId(),
                membership.getTitle(),
                membership.getStartDate(),
                membership.getEndDate(),
                membership.getValidity(),
                membership.getDiscount()
        );
    }

    public Membership findCurrent(Long clientId) {
        return jdbcTemplate.queryForObject(
                FIND_CURRENT_MEMBERSHIP_QUERY,
                new Object[]{clientId},
                (rs, rowNum) ->
                        new Membership(
                                rs.getLong(ID_FIELD),
                                rs.getString(TITLE_FIELD),
                                rs.getString(VALIDITY_FIELD),
                                rs.getTimestamp(START_DATE_FIELD),
                                rs.getTimestamp(END_DATE_FIELD),
                                rs.getInt(DISCOUNT_FIELD),
                                rs.getLong(CLIENT_ID_FIELD)
                        )
        );
    }

    public List<Membership> findAll(Long clientId) {
        return jdbcTemplate.query(
                String.format(FIND_CLIENT_MEMBERSHIPS_QUERY, clientId),
                (rs, rowNum) ->
                        new Membership(
                                rs.getLong(ID_FIELD),
                                rs.getString(TITLE_FIELD),
                                rs.getString(VALIDITY_FIELD),
                                rs.getTimestamp(START_DATE_FIELD),
                                rs.getTimestamp(END_DATE_FIELD),
                                rs.getInt(DISCOUNT_FIELD),
                                rs.getLong(ID_FIELD)
                        )
        );
    }

    public void update(Long id, LocalDateTime endDate) {
        jdbcTemplate.update(
                UPDATE_MEMBERSHIP_QUERY,
                endDate,
                id);
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE_MEMBERSHIP_QUERY, id);
    }

}