package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.Customer;
import com.MagicPost.example.BackendMagicPost.payload.CustomerRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.JwtAuthResponse;
import com.MagicPost.example.BackendMagicPost.payload.LoginDto;


import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;
import com.MagicPost.example.BackendMagicPost.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        //
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        // create access token => tra ve
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }


    // DTO = data transfer object => controller ~~ service
    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody CustomerRegisterDto registerDto){
        Customer customer = authService.register(registerDto);
        return new ResponseEntity<>(customer,HttpStatus.CREATED);

    }




}
