package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;

import java.util.List;

public interface ColManagerService {
     List<Package> getSentPackageInACollectionPoint();

     List<StaffDto> getAllStaffInACollectionPoint();

     List<Package> getCurrPackageInACollectionPoint();

     List<Package> getReceivePackageInACollectionPoint();

     Long getColPointIdOfCurrentStaff();
}
