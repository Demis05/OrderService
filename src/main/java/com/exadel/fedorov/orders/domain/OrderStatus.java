package com.exadel.fedorov.orders.domain;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

    PROCESSING("processing"),
    CANCELED("canceled"),
    COMPLETED("completed");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public static List<OrderStatus> getProductTypes() {
        return Arrays.asList(values());
    }

    public String getStatus() {
        return status;
    }

}