package com.springboot.mvc.tour_app.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String full_name;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;

}
