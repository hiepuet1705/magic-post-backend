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
@RequestMapping("api/manager")
public class TranManagerController {
    private TranManagerService tranManagerService;

    public TranManagerController(TranManagerService tranManagerService) {
        this.tranManagerService = tranManagerService;
    }

    @GetMapping("/tran/{id}/sent-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getSentPackagesInATransactionPoint(@PathVariable("id") Long tranPointId){

        List<Package> packages = tranManagerService.getSentPackageInATransactionPoint(tranPointId);
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/tran/{id}/curr-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getCurrPackagesInATransactionPoint(@PathVariable("id") Long tranId){
        List<Package> packages = tranManagerService.getCurrentPackagesInATransactionPoint(tranId);
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/tran/{id}/rec-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getReceivePackagesInATransactionPoint(@PathVariable("id") Long tranId){
        List<Package> packages = tranManagerService.getReceivePackagesInATransactionPoint(tranId);
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }

}
