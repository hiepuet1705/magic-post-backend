package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;

import java.util.List;

public interface ColManagerService {


     List<StaffDto> getAllStaffInACollectionPoint();

     Long getColPointIdOfCurrentStaff();
     String createAccountForStaffCol(StaffRegisterDto staffRegisterDto);
}
