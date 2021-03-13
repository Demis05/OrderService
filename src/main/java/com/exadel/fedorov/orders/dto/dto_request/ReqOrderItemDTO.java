package com.exadel.fedorov.orders.dto.dto_request;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ReqOrderItemDTO {

    Integer productId;
    BigDecimal price;
    Integer count;

}
