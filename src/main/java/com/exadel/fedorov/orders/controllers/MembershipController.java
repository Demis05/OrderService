package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.domain.Membership;
import com.exadel.fedorov.orders.dto.MembershipDTO;
import com.exadel.fedorov.orders.dto.dto_response.ClientDTO;
import com.exadel.fedorov.orders.service.MembershipService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/rest/memberships")
@RestController
public class MembershipController {

    public static final String SUCH_CLIENT_DOES_NOT_EXIST_MESSAGE = "";
    public static final String NO_CLIENTS_FOUND_MESSAGE = "";

    @Autowired
    private MembershipService membershipService;

    @GetMapping
    public ResponseEntity<List<MembershipDTO>> readAll(@PathVariable("id") Long clientId) {
        List<MembershipDTO> membershipDTOS = membershipService.findAll(clientId);
        if (membershipDTOS.isEmpty()) {
            return new ResponseEntity(NO_CLIENTS_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(membershipDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> readCurrent(@PathVariable("id") Long clientId) {
        if (clientId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<MembershipDTO> membershipDTO = membershipService.findCurrent(clientId);
        return membershipDTO.map(respOrderDTO -> new ResponseEntity(membershipDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(SUCH_CLIENT_DOES_NOT_EXIST_MESSAGE, HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Membership> create(@RequestBody MembershipDTO membershipDTO) {
        membershipService.create(membershipDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public ResponseEntity<Membership> update(@PathVariable("id") Long id, @RequestBody LocalDateTime endDate) {
        membershipService.update(id, endDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Membership> delete(@PathVariable("id") Long id) {
        membershipService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}