package com.exadel.fedorov.orders.dto.dto_request;

import lombok.Value;

@Value
public class NewClientDTO {

    String name;
    String login;
    String email;
    String phone;
    String address;

}
