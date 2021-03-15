package com.exadel.fedorov.orders.dto.dto_request;

import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

//@Value
@Getter
public class ReqOrderItemDTO {

    Integer productId;
    BigDecimal price;
    Integer count;

    public ReqOrderItemDTO(Integer productId, BigDecimal price, Integer count) {
        this.productId = productId;
        this.price = price;
        this.count = count;
    }

    public ReqOrderItemDTO() {
    }
}
