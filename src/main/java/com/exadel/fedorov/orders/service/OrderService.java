package com.exadel.fedorov.orders.service;

import com.exadel.fedorov.orders.domain.Order;
import com.exadel.fedorov.orders.dto.dto_request.ReqOrderDTO;
import com.exadel.fedorov.orders.repository.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderDAO orderDAO;

    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    public int create(Order order) {
        return orderDAO.create(order);
    }

    public Optional<Order> findById(Long id) {
        return orderDAO.findById(id);
    }

    public void update(Order order) {
        orderDAO.update(order);
    }

    public void deleteById(Long id) {
        orderDAO.deleteById(id);
    }

    public void createOrder(ReqOrderDTO reqOrderDTO) {
        orderDAO.createOrderWithProcedure(reqOrderDTO);
    }

}