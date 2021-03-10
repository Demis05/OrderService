package com.exadel.fedorov.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class OrderDTO {

    private final List<OrderPositionDTO> positions;
    private BigDecimal orderPrice;
    private Integer count;

    public OrderDTO(List<OrderPositionDTO> positions) {
        this.positions = positions;
        setCount();
        setOrderPrice();
    }

    private void setCount() {
        this.count = positions.size();
    }

    private void setOrderPrice() {
        for (OrderPositionDTO position : positions) {
            orderPrice = orderPrice.add(position.getTotalPrice());
        }
    }

}
