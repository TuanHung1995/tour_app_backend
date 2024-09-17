package com.springboot.mvc.tour_app.service;

import com.springboot.mvc.tour_app.payload.LoginDto;
import com.springboot.mvc.tour_app.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);

}
