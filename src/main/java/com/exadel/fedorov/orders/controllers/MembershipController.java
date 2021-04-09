package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.domain.Membership;
import com.exadel.fedorov.orders.dto.MembershipDTO;
import com.exadel.fedorov.orders.exception.InvalidDataException;
import com.exadel.fedorov.orders.exception.NoActiveMembershipException;
import com.exadel.fedorov.orders.exception.NoSuchDataException;
import com.exadel.fedorov.orders.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/rest/memberships")
@RestController
public class MembershipController {

    private static final String SUCH_MEMBERSHIP_DOES_NOT_EXIST_MESSAGE = "Active membership does not exist.";
    private static final String NO_MEMBERSHIP_FOUND_MESSAGE = "No memberships found.";
    private static final String CLIENT_ID_IS_NOT_VALID = "Client Id is not valid";

    @Autowired
    private MembershipService membershipService;

    @GetMapping
    public ResponseEntity<List<MembershipDTO>> readAll(@PathVariable("id") Long clientId) throws NoSuchDataException, InvalidDataException {
        if (null == clientId || clientId <= 0) {
            throw new InvalidDataException(CLIENT_ID_IS_NOT_VALID);
        }
        List<MembershipDTO> membershipDTOS = membershipService.findAll(clientId);
        if (membershipDTOS.isEmpty()) {
            throw new NoSuchDataException(NO_MEMBERSHIP_FOUND_MESSAGE);
        }
        return new ResponseEntity<>(membershipDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MembershipDTO> readCurrent(@PathVariable("id") Long clientId)
            throws InvalidDataException, NoActiveMembershipException {
        if (null == clientId || clientId <= 0) {
            throw new InvalidDataException(CLIENT_ID_IS_NOT_VALID);
        }
        Optional<MembershipDTO> membershipDTO = membershipService.findCurrent(clientId);
        if (!membershipDTO.isPresent()) {
            throw new NoActiveMembershipException(SUCH_MEMBERSHIP_DOES_NOT_EXIST_MESSAGE);
        }
        return new ResponseEntity<>(membershipDTO.get(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Membership> create(@RequestBody MembershipDTO membershipDTO) throws SQLException {
        System.out.println(membershipDTO);
        membershipService.create(membershipDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //rest/memberships/2222?end_date=2021-06-10T00:00
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public ResponseEntity<Membership> update(@PathVariable("id") Long id,
                                             @RequestParam("end_date")
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                     LocalDateTime endDate
    ) throws InvalidDataException {
        if (null == id || id <= 0) {
            throw new InvalidDataException(CLIENT_ID_IS_NOT_VALID);
        }
        membershipService.update(id, endDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Membership> delete(@PathVariable("id") Long id) throws InvalidDataException {
        if (null == id || id <= 0) {
            throw new InvalidDataException(CLIENT_ID_IS_NOT_VALID);
        }
        membershipService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}