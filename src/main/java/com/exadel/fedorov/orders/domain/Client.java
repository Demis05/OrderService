package com.exadel.fedorov.orders.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Client {

    @NonNull
    private Long id;
    @NonNull
    private String clientName;
    @NonNull
    private String login;

    private Long membershipId;
}


