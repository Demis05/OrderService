package com.exadel.fedorov.orders.dto.dto_request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ReqOrderItemDTO {

    Long productId;
    BigDecimal price;
    Integer count;

}