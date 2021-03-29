package com.exadel.fedorov.orders.dto.dto_request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReqOrderItemDTO {

    Long productId;
    BigDecimal positionPrice;
    Integer count;

}