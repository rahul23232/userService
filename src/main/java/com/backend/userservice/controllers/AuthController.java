package com.backend.userservice.controllers;

import com.backend.userservice.dtos.*;
import com.backend.userservice.exceptions.UserAlreadyExistsException;
import com.backend.userservice.exceptions.UserNotFoundException;
import com.backend.userservice.exceptions.WrongPasswordException;
import com.backend.userservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController (AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException {
        SignUpResponseDto response = new SignUpResponseDto();
        try {
            if(authService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword())){
                response.setRequestStatus(RequestStatus.SUCCESS);
            } else {
                response.setRequestStatus(RequestStatus.FAILURE);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            response.setRequestStatus(RequestStatus.FAILURE);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFoundException, WrongPasswordException {

        try {
            String token = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            LoginResponseDto loginDto = new LoginResponseDto();
            loginDto.setRequestStatus(RequestStatus.SUCCESS);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("AUTH_TOKEN", token);

            ResponseEntity<LoginResponseDto> response = new ResponseEntity<>(
                    loginDto, headers, HttpStatus.OK
            );
            return response;
        } catch (Exception e){
            LoginResponseDto loginDto = new LoginResponseDto();
            loginDto.setRequestStatus(RequestStatus.FAILURE);
            ResponseEntity<LoginResponseDto> response = new ResponseEntity<>(
                    loginDto, null , HttpStatus.BAD_REQUEST
            );
            return response;
        }

    }

    @GetMapping("/validate")
    public boolean validate(@RequestParam("token") String token){
        return authService.validate(token);
    }

}
