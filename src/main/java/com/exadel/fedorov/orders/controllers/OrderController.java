package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.domain.Order;
import com.exadel.fedorov.orders.dto.dto_request.ReqOrderItemDTO;
import com.exadel.fedorov.orders.dto.dto_response.RespOrderDTO;
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
    private static final String SUCH_ORDER_DOES_NOT_EXIST_MESSAGE = "Such order does not exist.";
    private static final String NO_ORDERS_FOUND_MESSAGE = "No orders found.";
    private static final String INCORRECT_INPUT_DATA_MESSAGE = "Incorrect input data.";

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<RespOrderDTO>> readAll() {
        List<RespOrderDTO> orders = orderService.findAll();
        if (orders.isEmpty()) {
            return new ResponseEntity(NO_ORDERS_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RespOrderDTO> read(@PathVariable("id") Long orderId) {
        if (orderId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<RespOrderDTO> orderDto = orderService.findById(orderId);
        return orderDto.map(respOrderDTO -> new ResponseEntity(respOrderDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(SUCH_ORDER_DOES_NOT_EXIST_MESSAGE, HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody List<ReqOrderItemDTO> orderItems) {
        if (orderItems.isEmpty()) {
            return new ResponseEntity(INCORRECT_INPUT_DATA_MESSAGE, HttpStatus.BAD_REQUEST);
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