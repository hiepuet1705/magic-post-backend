package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.payload.JwtAuthResponse;
import com.MagicPost.example.BackendMagicPost.payload.LoginDto;
import com.MagicPost.example.BackendMagicPost.payload.RegisterDto;
import com.MagicPost.example.BackendMagicPost.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse>login(@RequestBody LoginDto loginDto){
        //
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        // create access token => tra ve
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }


    // DTO = data transfer object => controller ~~ service
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

}
