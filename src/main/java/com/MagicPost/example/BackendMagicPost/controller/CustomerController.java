package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.PackageInfoDto;
import com.MagicPost.example.BackendMagicPost.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/packages/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Package>> getAllPackagesByCustomerId(@PathVariable("customerId") Long customerId){
        List<Package> packages = customerService.getAllPackagesByCustomerId(customerId);
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }
    @GetMapping("/package/{packageIdHash}")

    public ResponseEntity<PackageInfoDto> getPackageFlowByPackageId(@PathVariable("packageIdHash") String packageIdHash){
        PackageInfoDto PackageInfoDto = customerService.trackingSinglePackage(packageIdHash);
        return new ResponseEntity<>(PackageInfoDto, HttpStatus.OK);
    }


}
