package com.exadel.fedorov.orders.dto.dto_response;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class RespOrderItemDTO {

    Integer productId;
    BigDecimal totalPrice;
    Integer count;

}
