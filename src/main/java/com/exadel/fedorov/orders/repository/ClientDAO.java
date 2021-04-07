package com.exadel.fedorov.orders.repository;

import com.exadel.fedorov.orders.domain.Client;
import com.exadel.fedorov.orders.domain.Contact;
import com.exadel.fedorov.orders.dto.dto_response.ClientDTO;
import com.exadel.fedorov.orders.exception.NoSuchDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDAO {

    public static final String CREATE_CLIENT_FUNCTION = "create_client_function";
    public static final String UPDATE_CLIENT_QUERY = "UPDATE clients SET name=?, login=? WHERE id=?;";
    public static final String UPDATE_CLIENT_CONTACT_QUERY = "UPDATE contacts SET email=?, phone=?, address=? WHERE client_id=?;";
    public static final String DELETE_CLIENT_QUERY = "DELETE FROM clients where id=?;";
    public static final String FIND_ALL_QUERY = "SELECT * FROM clients INNER JOIN contacts on clients.id = contacts.client_id;";
    public static final String FIND_BY_ID_QUERY = "SELECT clients.id, clients.name, clients.login,contacts.email,contacts.phone,contacts.address FROM clients INNER JOIN contacts ON clients.id = contacts.client_id WHERE clients.id=?;";

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String LOGIN_FIELD = "login";
    public static final String EMAIL_FIELD = "email";
    public static final String PHONE_FIELD = "phone";
    public static final String ADDRESS_FIELD = "address";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcCall = new SimpleJdbcCall(dataSource);
    }

    public void create(Client client, Contact contact) {
        simpleJdbcCall = simpleJdbcCall.withProcedureName(CREATE_CLIENT_FUNCTION);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue(NAME_FIELD, client.getName())
                .addValue(LOGIN_FIELD, client.getLogin())
                .addValue(EMAIL_FIELD, contact.getEmail())
                .addValue(PHONE_FIELD, contact.getPhone())
                .addValue(ADDRESS_FIELD, contact.getAddress());
        simpleJdbcCall.execute(in);
    }

    public ClientDTO findDTOById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    FIND_BY_ID_QUERY,
                    new Object[]{id},
                    (rs, rowNum) ->
                            new ClientDTO(
                                    rs.getLong(ID_FIELD),
                                    rs.getString(LOGIN_FIELD),
                                    rs.getString(NAME_FIELD),
                                    rs.getString(EMAIL_FIELD),
                                    rs.getString(PHONE_FIELD),
                                    rs.getString(ADDRESS_FIELD)
                            )
            );
        } catch (EmptyResultDataAccessException exc) {
            return null;
        }
    }

    public List<ClientDTO> findAllDTOs() {
        try {
            return jdbcTemplate.query(
                    FIND_ALL_QUERY,
                    (rs, rowNum) ->
                            new ClientDTO(
                                    rs.getLong(ID_FIELD),
                                    rs.getString(LOGIN_FIELD),
                                    rs.getString(NAME_FIELD),
                                    rs.getString(EMAIL_FIELD),
                                    rs.getString(PHONE_FIELD),
                                    rs.getString(ADDRESS_FIELD)
                            )
            );
        } catch (EmptyResultDataAccessException exc) {
            return new ArrayList<>();
        }
    }

    public void updateClient(Client client) {
        jdbcTemplate.update(
                UPDATE_CLIENT_QUERY,
                client.getName(),
                client.getLogin(),
                client.getId());
    }

    public void updateContact(Contact contact) {
        jdbcTemplate.update(
                UPDATE_CLIENT_CONTACT_QUERY,
                contact.getEmail(),
                contact.getPhone(),
                contact.getAddress(),
                contact.getClientId());
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE_CLIENT_QUERY, id);
    }

}