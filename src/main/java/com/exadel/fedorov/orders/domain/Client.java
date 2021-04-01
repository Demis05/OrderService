package com.exadel.fedorov.orders.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String login;

}