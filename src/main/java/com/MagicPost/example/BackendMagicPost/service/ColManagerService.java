package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;

import java.util.List;

public interface ColManagerService {
     List<Package> getSentPackageInACollectionPoint();

     List<Package> getCurrPackageInACollectionPoint();

     List<Package> getReceivePackageInACollectionPoint();

     Long getColPointIdOfCurrentStaff();
}
