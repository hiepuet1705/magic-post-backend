package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.PackageFlowDto;

import java.util.List;

public interface CustomerService {
    List<Package> getAllPackagesByCustomerId(Long customerId);
    PackageFlowDto trackingSinglePackage(Long packageId);
}
