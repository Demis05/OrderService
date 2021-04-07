package com.exadel.fedorov.orders.dto.dto_request;

import lombok.Data;

@Data
public class NewClientDTO {

    String name;
    String login;
    String email;
    String phone;
    String address;

}
