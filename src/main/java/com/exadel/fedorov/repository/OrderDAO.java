package com.exadel.fedorov.repository;

import com.exadel.fedorov.domain.Order;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Repository
public class OrderDAO {

    public Order create(Order order) {

        return null;
    }

    public Order findById(Long id) {

        return new Order();
    }

    public void update(Order order) {


    }

    public void deleteById(Long id) {


    }

    public List<Order> findAll() {

        return new ArrayList<>();
    }
}
