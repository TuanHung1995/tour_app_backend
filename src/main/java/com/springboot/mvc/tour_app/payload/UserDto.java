package com.springboot.mvc.tour_app.payload;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String full_name;
    private String phone;
    private String address;

}
