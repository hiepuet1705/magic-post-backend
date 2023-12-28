package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Customer;
import com.MagicPost.example.BackendMagicPost.entity.User;
import com.MagicPost.example.BackendMagicPost.payload.CustomerRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.LoginDto;

import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.UserDto;

public interface AuthService {
    String login(LoginDto loginDto);

    UserDto changePassword(UserDto user);

    Customer register(CustomerRegisterDto registerDto);

}
