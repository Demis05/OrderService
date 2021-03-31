package com.exadel.fedorov.orders.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Contact {

    @NonNull
    private String email;
    @NonNull
    private String phone;
    @NonNull
    private String address;
    @NonNull
    private Long clientId;

}