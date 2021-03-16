package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.domain.Order;
import com.exadel.fedorov.orders.dto.dto_request.ReqOrderDTO;
import com.exadel.fedorov.orders.dto.dto_request.ReqOrderItemDTO;
import com.exadel.fedorov.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/rest/orders")
@RestController
public class OrderController {

    private static final String DEFAULT_CLIENT_NAME = "FirstName LastName";
    private static final String DEFAULT_STATUS_DESCRIPTION = "OK";
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.findAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long orderId) {
        if (orderId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Order> order = orderService.findById(orderId);
        if (!order.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Order> saveOrder(@RequestBody List<ReqOrderItemDTO> orderItems) {
        if (orderItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderService.createOrder(
                new ReqOrderDTO(orderItems, new BigDecimal(200), DEFAULT_CLIENT_NAME, DEFAULT_STATUS_DESCRIPTION));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        HttpHeaders headers = new HttpHeaders();

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (Objects.nonNull(orderService.findById((order.getId())))) {
            orderService.update(order);
        }
        return new ResponseEntity<>(order, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") Long id) {
        Optional<Order> order = orderService.findById(id);
        if (!order.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
