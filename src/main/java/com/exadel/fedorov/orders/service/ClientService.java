package com.exadel.fedorov.orders.service;

import com.exadel.fedorov.orders.domain.Client;
import com.exadel.fedorov.orders.domain.Contact;
import com.exadel.fedorov.orders.dto.dto_request.ReqClientDTO;
import com.exadel.fedorov.orders.dto.dto_response.RespClientDTO;
import com.exadel.fedorov.orders.repository.ClientDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientService {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private ModelMapper modelMapper;

    public void createClient(ReqClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);
        Contact contact = modelMapper.map(clientDTO, Contact.class);
        clientDAO.create(client, contact);
    }

    public Optional<RespClientDTO> findById(Long clientId) {
        Client client = clientDAO.findById(clientId);
        RespClientDTO respClientDTO = modelMapper.map(client, RespClientDTO.class);
        return Optional.of(respClientDTO);
    }

    public List<RespClientDTO> findAll() {
        List<Client> clients = clientDAO.findAll();
        List<RespClientDTO> clientDTOS = new ArrayList<>();
        for (Client client : clients) {
            clientDTOS.add(modelMapper.map(client, RespClientDTO.class));
        }
        return clientDTOS;
    }

    public void update(Long id, String name, String login) {
        clientDAO.update(id, name, login);
    }

    public void deleteById(Long id) {
        clientDAO.deleteById(id);
    }
}
