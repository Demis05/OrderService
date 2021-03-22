package com.exadel.fedorov.orders.dto.dto_response;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class RespOrderItemDTO {

    Long productId;
    BigDecimal totalPrice;
    Integer count;

}
