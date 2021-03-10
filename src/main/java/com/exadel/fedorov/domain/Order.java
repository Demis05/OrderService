package com.exadel.fedorov.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Order {

    private Long id;
    private BigDecimal totalPrice;
    private Timestamp time;
    private OrderStatus status;
    private String statusDescription;

    public Order() {
    }

    public Order(BigDecimal totalPrice, Timestamp time, OrderStatus status, String statusDescription) {
        this.totalPrice = totalPrice;
        this.time = time;
        this.status = status;
        this.statusDescription = statusDescription;
    }
}
