package com.exadel.fedorov.orders.domain;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Order {

    private Long id;

    private  Timestamp time;
    private OrderStatus status;
    @NonNull
    private String clientName;
    @NonNull
    private BigDecimal totalPrice;
    @NonNull
    private String statusDescription;
}
