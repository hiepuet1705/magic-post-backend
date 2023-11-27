package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.payload.LoginDto;
import com.MagicPost.example.BackendMagicPost.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
