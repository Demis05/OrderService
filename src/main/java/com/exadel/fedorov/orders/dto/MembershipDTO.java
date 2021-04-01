package com.exadel.fedorov.orders.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MembershipDTO {

    Long id;
    String title;
    String validity;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer discount;

}