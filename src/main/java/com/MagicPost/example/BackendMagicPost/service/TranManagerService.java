package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;

import java.util.List;

public interface TranManagerService {
    public TransactionPoint getTransactionPointByManagerId(Long managerId);

    List<StaffDto> getAllStaffInATransactionPoint();


    Long getTranPointIdOfCurrentStaff();


    String createAccountForStaffTran(StaffRegisterDto staffRegisterDto);

}
