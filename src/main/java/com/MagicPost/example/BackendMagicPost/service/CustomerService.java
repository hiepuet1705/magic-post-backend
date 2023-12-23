package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;

import java.util.List;

public interface CustomerService {
    List<Package> getAllPackagesByCustomerId(Long customerId);
    List<Package> trackingSinglePackage(Long packageId);
}
