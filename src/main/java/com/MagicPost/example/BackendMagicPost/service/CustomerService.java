package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.PackageInfoDto;
import com.MagicPost.example.BackendMagicPost.payload.PackageInfoDto;

import java.util.List;

public interface CustomerService {
    List<Package> getAllPackagesByCustomerId(Long customerId);
    PackageInfoDto trackingSinglePackage(String packageIdHash);
}
