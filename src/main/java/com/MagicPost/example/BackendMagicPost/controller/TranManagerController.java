package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.service.TranManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
