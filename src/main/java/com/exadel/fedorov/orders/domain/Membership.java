package com.exadel.fedorov.orders.domain;

import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Membership {

    @NonNull
    Long id;
    @NonNull
    String title;
    @NonNull
    String validity;
    @NonNull
    Timestamp startDate;
    @NonNull
    Timestamp endDate;
    @NonNull
    Integer discount;
    @NonNull
    Long clientId;

}