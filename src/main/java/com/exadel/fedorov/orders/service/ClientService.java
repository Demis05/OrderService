package com.exadel.fedorov.orders.service;

import com.exadel.fedorov.orders.domain.Client;
import com.exadel.fedorov.orders.domain.Contact;
import com.exadel.fedorov.orders.dto.dto_request.NewClientDTO;
import com.exadel.fedorov.orders.dto.dto_response.ClientDTO;
import com.exadel.fedorov.orders.repository.ClientDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ClientService {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private ModelMapper modelMapper;

    public void create(NewClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);
        Contact contact = modelMapper.map(clientDTO, Contact.class);
        clientDAO.create(client, contact);
    }

    public Optional<ClientDTO> findById(Long clientId) {
        Client client = clientDAO.findById(clientId);
        ClientDTO respClientDTO = modelMapper.map(client, ClientDTO.class);
        return Optional.of(respClientDTO);
    }

    public List<ClientDTO> findAll() {
        return clientDAO.findDTOs();
    }

    public void update(Long id, NewClientDTO updateClient) {
//        public void update(Long id, String name, String login, String email, String phone, String address) {
        clientDAO.updateClient(new Client(id, updateClient.getName(), updateClient.getLogin()));
        clientDAO.updateContact(new Contact(updateClient.getEmail(), updateClient.getPhone(), updateClient.getAddress(), id));
    }

    public void delete(Long id) {
        clientDAO.delete(id);
    }

}