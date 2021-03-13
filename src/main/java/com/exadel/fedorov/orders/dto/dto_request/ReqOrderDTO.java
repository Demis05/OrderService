package com.exadel.fedorov.orders.dto.dto_request;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class ReqOrderDTO {

    List<ReqOrderItemDTO> positions;
    BigDecimal total;
    String clientName;

}
