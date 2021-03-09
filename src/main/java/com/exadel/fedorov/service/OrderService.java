package com.exadel.fedorov.service;

import com.exadel.fedorov.domain.Order;
import com.exadel.fedorov.repository.OrderDAO;
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

    public Order create(Order order) {
        return orderDAO.create(order);
    }

    public Order findById(Long id) {
        return orderDAO.findById(id);
    }


    public void update(Order order) {
        orderDAO.update(order);
    }

    public void deleteById(Long id) {
        orderDAO.deleteById(id);
    }

}