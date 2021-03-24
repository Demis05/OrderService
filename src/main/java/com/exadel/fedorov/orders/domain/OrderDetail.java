package com.exadel.fedorov.orders.domain;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class OrderDetail {

    private Long id;

    private Long orderId;
    @NonNull
    private BigDecimal positionPrice;
    @NonNull
    private Long productId;
    @NonNull
    private Integer productCount;

    public OrderDetail(BigDecimal positionPrice, Long orderId, Long productId, Integer productCount) {
        this.positionPrice = positionPrice;
        this.orderId = orderId;
        this.productId = productId;
        this.productCount = productCount;
    }

    public OrderDetail(BigDecimal positionPrice, Long productId, Integer productCount) {
        this.positionPrice = positionPrice;
        this.productId = productId;
        this.productCount = productCount;
    }
}
