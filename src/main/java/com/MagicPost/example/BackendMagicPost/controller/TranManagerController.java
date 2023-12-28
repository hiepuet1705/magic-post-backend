package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;
import com.MagicPost.example.BackendMagicPost.service.TranManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/manager/tran")
public class TranManagerController {
    private TranManagerService tranManagerService;

    public TranManagerController(TranManagerService tranManagerService) {
        this.tranManagerService = tranManagerService;
    }

    @GetMapping("/sent-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getSentPackagesInATransactionPoint(){

        List<Package> packages = tranManagerService.getSentPackageInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/curr-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getCurrPackagesInATransactionPoint(){
        List<Package> packages = tranManagerService.getCurrentPackagesInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/rec-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getReceivePackagesInATransactionPoint(){
        List<Package> packages = tranManagerService.getReceivePackagesInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/staff")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<StaffDto>> getAllStaffInATransactionPoint(){
        List<StaffDto> staffDtos = tranManagerService.getAllStaffInATransactionPoint();
        return new ResponseEntity<>(staffDtos, HttpStatus.OK);

    }
    @PostMapping("/register/staff")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<String> createAccountForStaffTran(@RequestBody StaffRegisterDto staffRegisterDto){
        String response = tranManagerService.createAccountForStaffTran(staffRegisterDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
