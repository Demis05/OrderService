package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.domain.Order;
import com.exadel.fedorov.orders.dto.dto_request.ReqOrderItemDTO;
import com.exadel.fedorov.orders.dto.dto_response.RespOrderDTO;
import com.exadel.fedorov.orders.exception.InvalidDataException;
import com.exadel.fedorov.orders.exception.NoSuchDataException;
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
    private static final String ORDER_ID_IS_NOT_VALID = "Order Id is not valid";

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<RespOrderDTO>> readAll() throws NoSuchDataException {
        List<RespOrderDTO> orders = orderService.findAll();
        if (orders.isEmpty()) {
            throw new NoSuchDataException(NO_ORDERS_FOUND_MESSAGE);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RespOrderDTO> read(@PathVariable("id") Long orderId) throws NoSuchDataException, InvalidDataException {
        if (null == orderId || orderId <= 0) {
            throw new InvalidDataException(ORDER_ID_IS_NOT_VALID);
        }
        Optional<RespOrderDTO> orderDto = orderService.findById(orderId);
        if (!orderDto.isPresent()) {
            throw new NoSuchDataException(SUCH_ORDER_DOES_NOT_EXIST_MESSAGE);
        }
        return new ResponseEntity<>(orderDto.get(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody List<ReqOrderItemDTO> orderItems) throws InvalidDataException {
        if (orderItems.isEmpty()) {
            throw new InvalidDataException(INCORRECT_INPUT_DATA_MESSAGE);
        }
        orderService.createOrder(orderItems, DEFAULT_CLIENT_NAME, DEFAULT_STATUS_DESCRIPTION);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> update(@PathVariable("id") Long id,
                                        @RequestBody String status,
                                        @RequestBody String statusDescription) throws InvalidDataException {
        if (null == id || id <= 0) {
            throw new InvalidDataException(ORDER_ID_IS_NOT_VALID);
        }
        orderService.update(id, status, statusDescription);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") Long id) throws InvalidDataException {
        if (null == id || id <= 0) {
            throw new InvalidDataException(ORDER_ID_IS_NOT_VALID);
        }
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}