package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.domain.Order;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/rest/orders")
@RestController
public class OrderController {

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
        System.out.println(orderId);
        Optional<Order> order = orderService.findById(orderId);
        if (!order.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(order, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
//    public ResponseEntity<Order> saveOrder(@RequestBody List<ReqOrderItemDTO> orderItems) {
    public ResponseEntity<Order> saveOrder(@RequestBody ReqOrderItemDTO item) {

        ArrayList<ReqOrderItemDTO> orderItems = new ArrayList<>();
        orderItems.add(new ReqOrderItemDTO(1, new BigDecimal(111.00), 1));
        orderItems.add(new ReqOrderItemDTO(2, new BigDecimal(112.00), 1));
        orderItems.add(new ReqOrderItemDTO(3, new BigDecimal(113.00), 1));

        System.out.println(orderItems.size());
        if (orderItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String clientName = "FirstName LastName";
        BigDecimal price = new BigDecimal(200);

        Order order = new Order(clientName, price, "OK");

        orderService.createOrder(order, orderItems);
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
