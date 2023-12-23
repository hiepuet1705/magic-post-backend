package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.PackageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService{
    private PackageRepository packageRepository;

    public CustomerServiceImp(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public List<Package> getAllPackagesByCustomerId(Long customerId) {

        List<Package> packages = packageRepository.getPackagesByCustomerId(customerId);


        return packages;
    }

    @Override
    public List<Package> trackingSinglePackage(Long packageId) {
        // find receipt
        return null;
    }
}
