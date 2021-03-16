package com.exadel.fedorov.orders.dto.dto_response;

import lombok.Value;
import java.math.BigDecimal;
import java.util.List;

@Value
public class RespOrderDTO {

    List<RespOrderItemDTO> positions;
    BigDecimal orderPrice;
    Integer count;
    String clientName;

}
