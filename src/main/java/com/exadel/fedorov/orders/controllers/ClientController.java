package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.domain.Client;
import com.exadel.fedorov.orders.dto.dto_request.NewClientDTO;
import com.exadel.fedorov.orders.dto.dto_response.ClientDTO;
import com.exadel.fedorov.orders.exception.InvalidDataException;
import com.exadel.fedorov.orders.exception.NoSuchDataException;
import com.exadel.fedorov.orders.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/rest/clients")
@RestController
public class ClientController {

    private static final String SUCH_CLIENT_DOES_NOT_EXIST_MESSAGE = "Such client does not exist.";
    private static final String NO_CLIENTS_FOUND_MESSAGE = "No clients found.";
    private static final String CLIENT_ID_IS_NOT_VALID = "Client Id is not valid";

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> readAll() throws NoSuchDataException {
        List<ClientDTO> clientDTOS = clientService.findAll();
        if (clientDTOS.isEmpty()) {
            throw new NoSuchDataException(NO_CLIENTS_FOUND_MESSAGE);
        }
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> read(@PathVariable("id") Long id) throws NoSuchDataException, InvalidDataException {
        if (null == id || id <= 0) {
            throw new InvalidDataException(CLIENT_ID_IS_NOT_VALID);
        }
        Optional<ClientDTO> clientDTO = clientService.findById(id);
        if (!clientDTO.isPresent()) {
            throw new NoSuchDataException(SUCH_CLIENT_DOES_NOT_EXIST_MESSAGE);
        }
        return new ResponseEntity<>(clientDTO.get(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Client> create(@RequestBody NewClientDTO clientDTO) {
        clientService.create(clientDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") Long id, @RequestBody NewClientDTO updateClient) throws InvalidDataException {
        if (null == id || id <= 0) {
            throw new InvalidDataException(CLIENT_ID_IS_NOT_VALID);
        }
        clientService.update(id, updateClient);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Client> deleteClient(@PathVariable("id") Long id) throws InvalidDataException {
        if (null == id || id <= 0) {
            throw new InvalidDataException(CLIENT_ID_IS_NOT_VALID);
        }
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}