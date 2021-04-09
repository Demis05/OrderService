package com.exadel.fedorov.orders.domain;

import lombok.Data;
import lombok.NonNull;
import org.postgresql.util.PGInterval;

import java.sql.Timestamp;

@Data
public class Membership {

    @NonNull
    Long id;
    @NonNull
    String title;
    @NonNull
    PGInterval validity;
    @NonNull
    Timestamp startDate;
    @NonNull
    Timestamp endDate;
    @NonNull
    Integer discount;
    @NonNull
    Long clientId;

}