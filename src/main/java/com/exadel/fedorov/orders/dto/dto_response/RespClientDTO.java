package com.exadel.fedorov.orders.dto.dto_response;

import lombok.Value;

@Value
public class RespClientDTO {

    Long id;
    String login;
    String name;
    Long membershipId;
    String email;

    String phone;
    String address;
}
