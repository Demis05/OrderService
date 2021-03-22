package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.domain.Order;
import com.exadel.fedorov.orders.dto.dto_request.ReqOrderItemDTO;
import com.exadel.fedorov.orders.dto.dto_response.RespOrderDTO;
import com.exadel.fedorov.orders.dto.dto_response.RespOrderItemDTO;
import com.exadel.fedorov.orders.service.OrderService;
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

@RequestMapping("/rest/orders")
@RestController
public class OrderController {

    private static final String DEFAULT_CLIENT_NAME = "FirstName LastName";
    private static final String DEFAULT_STATUS_DESCRIPTION = "OK";

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<RespOrderItemDTO>> readAll() {
        List<RespOrderDTO> orders = orderService.findAll();
        System.out.println(orders);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RespOrderItemDTO> read(@PathVariable("id") Long orderId) {
        if (orderId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<RespOrderDTO> orderDto = orderService.findById(orderId);
        if (!orderDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orderDto.get(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody List<ReqOrderItemDTO> orderItems) {
        if (orderItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderService.createOrder(orderItems, DEFAULT_CLIENT_NAME, DEFAULT_STATUS_DESCRIPTION);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> update(@PathVariable("id") Long id, @RequestBody String status, @RequestBody String statusDescription) {
        orderService.update(id, status, statusDescription);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
