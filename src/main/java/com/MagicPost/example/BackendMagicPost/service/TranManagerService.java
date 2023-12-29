package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.controller.dtos.user.EditStaffRequest;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;

import java.util.List;

public interface TranManagerService {
    public TransactionPoint getTransactionPointByManagerId(Long managerId);

    List<StaffDto> getAllStaffInATransactionPoint();

    StaffDto getStaffById(Long staffId);

    String editStaff(Long staffId, EditStaffRequest editStaffRequest);

    String deleteStaff(Long staffId);

    Long getTranPointIdOfCurrentStaff();


    String createAccountForStaffTran(StaffRegisterDto staffRegisterDto);

}
