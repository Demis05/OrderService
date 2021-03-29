package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.domain.Client;
import com.exadel.fedorov.orders.dto.dto_request.ReqClientDTO;
import com.exadel.fedorov.orders.dto.dto_response.RespClientDTO;
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

    public static final String SUCH_CLIENT_DOES_NOT_EXIST_MESSAGE = "";
    public static final String NO_CLIENTS_FOUND_MESSAGE = "";

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<RespClientDTO>> readAll() {
        List<RespClientDTO> clientDTOS = clientService.findAll();
        if (clientDTOS.isEmpty()) {
            return new ResponseEntity(NO_CLIENTS_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(clientDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RespClientDTO> read(@PathVariable("id") Long clientId) {
        if (clientId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<RespClientDTO> clientDTO = clientService.findById(clientId);
        return clientDTO.map(respOrderDTO -> new ResponseEntity(clientDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(SUCH_CLIENT_DOES_NOT_EXIST_MESSAGE, HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Client> create(@RequestBody ReqClientDTO clientDTO) {
        //VALIDATION


        clientService.createClient(clientDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") Long id, @RequestBody String name, @RequestBody String login) {
        clientService.update(id, name, login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Client> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
