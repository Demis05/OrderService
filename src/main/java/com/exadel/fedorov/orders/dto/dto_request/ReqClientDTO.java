package com.exadel.fedorov.orders.dto.dto_request;

import lombok.Value;

@Value
public class ReqClientDTO {

    String name;
    String login;

    String email;
    String phone;
    String address;

}
