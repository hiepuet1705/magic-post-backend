package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;
import com.MagicPost.example.BackendMagicPost.service.ColManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/manager/col")
public class ColManagerController {
    private ColManagerService colManagerService;

    public ColManagerController(ColManagerService colManagerService) {
        this.colManagerService = colManagerService;
    }

    @GetMapping("/sent-packages")
    @PreAuthorize("hasRole('HEADCOL')")
    public ResponseEntity<List<Package>> getSentPackagesInACollectionPoint(){

        List<Package> packages = colManagerService.getSentPackageInACollectionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/curr-packages")
    @PreAuthorize("hasRole('HEADCOL')")
    public ResponseEntity<List<Package>> getCurrPackagesInACollectionPoint(){
        List<Package> packages = colManagerService.getCurrPackageInACollectionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/rec-packages")
    @PreAuthorize("hasRole('HEADCOL')")
    public ResponseEntity<List<Package>> getReceivePackageInACollectionPoint(){
        List<Package> packages = colManagerService.getReceivePackageInACollectionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/staff")
    @PreAuthorize("hasRole('HEADCOL')")
    public ResponseEntity<List<StaffDto>> getAllStaffInACollectionPoint(){
        List<StaffDto> staffDtos = colManagerService.getAllStaffInACollectionPoint();
        return new ResponseEntity<>(staffDtos, HttpStatus.OK);

    }
    @PostMapping("/register/staff")
    @PreAuthorize("hasRole('HEADCOL')")
    public ResponseEntity<String> createAccountForStaffCol(@RequestBody StaffRegisterDto staffRegisterDto){
        String response = colManagerService.createAccountForStaffCol(staffRegisterDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }



}
