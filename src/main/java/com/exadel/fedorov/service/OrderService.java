package com.exadel.fedorov.service;

import com.exadel.fedorov.domain.Order;
import com.exadel.fedorov.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public int create(Order order) {
        return orderRepository.create(order);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }


    public void update(Order order) {
        orderRepository.update(order);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

}