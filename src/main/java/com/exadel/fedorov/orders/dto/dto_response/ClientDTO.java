package com.exadel.fedorov.orders.dto.dto_response;

import lombok.Value;

@Value
public class ClientDTO {

    Long id;
    String login;
    String name;

    String email;
    String phone;
    String address;

}
