package com.exadel.fedorov.orders.dto.dto_response;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class RespOrderDTO {

    List<RespOrderItemDTO> positions;
    BigDecimal totalPrice;
    String clientName;
    String status;
    String statusDescription;
    LocalDateTime time;

}
