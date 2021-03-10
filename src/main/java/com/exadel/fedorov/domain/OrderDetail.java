package com.exadel.fedorov.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetail {

    private int id;
    private int orderId;
    private BigDecimal positionPrice;
    private Integer productId;
    private Integer productCount;

    public OrderDetail(int orderId, BigDecimal positionPrice, Integer productId, Integer productCount) {
        this.orderId = orderId;
        this.positionPrice = positionPrice;
        this.productId = productId;
        this.productCount = productCount;
    }

    public OrderDetail() {
    }
}
