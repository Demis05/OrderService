package com.exadel.fedorov.orders.repository;

import com.exadel.fedorov.orders.domain.Client;
import com.exadel.fedorov.orders.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcCall = new SimpleJdbcCall(dataSource);
    }

    public void deleteById(Long id) {


    }

    public Client findById(Long clientId) {

        Client client = new Client(0L,"","");
        return client;
    }

    public void create(Client client, Contact contact) {


    }

    public List<Client> findAll() {
        List<Client> clients = new ArrayList();

        return clients;
    }

    public void update(Long id, String name, String login) {


    }
}
