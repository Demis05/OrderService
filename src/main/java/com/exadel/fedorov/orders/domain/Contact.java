package com.exadel.fedorov.orders.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
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