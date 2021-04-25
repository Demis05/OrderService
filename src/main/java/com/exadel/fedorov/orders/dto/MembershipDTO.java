package com.exadel.fedorov.orders.dto;

import lombok.Data;

@Data
public class MembershipDTO {

    Long clientId;
    String title;
    String validity;
    String startDate;
    String endDate;
    Integer discount;

}