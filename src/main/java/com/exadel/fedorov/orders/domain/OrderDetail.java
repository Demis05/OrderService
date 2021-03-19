package com.exadel.fedorov.orders.domain;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class OrderDetail {

    private int id;

    @NonNull
    private BigDecimal positionPrice;
    @NonNull
    private int orderId;
    @NonNull
    private Integer productId;
    @NonNull
    private Integer productCount;
}
