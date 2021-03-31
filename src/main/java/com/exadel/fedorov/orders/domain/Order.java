package com.exadel.fedorov.orders.domain;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Order {

    private Long id;
    private Timestamp time;
    private OrderStatus status;
    @NonNull
    private String clientName;
    @NonNull
    private BigDecimal totalPrice;
    @NonNull
    private String statusDescription;

    public Order(@NonNull String clientName, @NonNull BigDecimal totalPrice, @NonNull String statusDescription) {
        this.clientName = clientName;
        this.totalPrice = totalPrice;
        this.statusDescription = statusDescription;
    }

    public Order(Timestamp time, OrderStatus status, @NonNull String clientName,
                 @NonNull BigDecimal totalPrice, @NonNull String statusDescription) {
        this.time = time;
        this.status = status;
        this.clientName = clientName;
        this.totalPrice = totalPrice;
        this.statusDescription = statusDescription;
    }

    public Order(Long id, Timestamp time, OrderStatus status,
                 @NonNull String clientName, @NonNull BigDecimal totalPrice, @NonNull String statusDescription) {
        this.id = id;
        this.time = time;
        this.status = status;
        this.clientName = clientName;
        this.totalPrice = totalPrice;
        this.statusDescription = statusDescription;
    }

}