package com.exadel.fedorov.orders.dto.dto_response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RespOrderItemDTO {

    Long productId;
    BigDecimal positionPrice;
    Integer count;

}
