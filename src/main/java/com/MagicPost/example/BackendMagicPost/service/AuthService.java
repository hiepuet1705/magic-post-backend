package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.payload.CustomerRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.LoginDto;

import com.MagicPost.example.BackendMagicPost.payload.StaffTranRegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(CustomerRegisterDto registerDto);

    String createAccountForStaffTran(StaffTranRegisterDto staffRegisterDto);
    String createAccountForStaffCol(StaffTranRegisterDto staffRegisterDto);
}
