package com.backend.userservice.controllers;

import com.backend.userservice.dtos.SignUpRequestDto;
import com.backend.userservice.dtos.SignUpResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/sign_up")
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        return null;
    }

    @PostMapping("/login")
    public String login(){
        return "login";
    }

}
