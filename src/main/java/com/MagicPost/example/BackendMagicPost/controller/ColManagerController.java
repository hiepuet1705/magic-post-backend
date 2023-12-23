package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.service.ColManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/manager/col")
public class ColManagerController {
    private ColManagerService colManagerService;

    public ColManagerController(ColManagerService colManagerService) {
        this.colManagerService = colManagerService;
    }

    @GetMapping("/{id}/sent-packages")
    @PreAuthorize("hasRole('HEADCOL)")
    public ResponseEntity<List<Package>> getSentPackagesInACollectionPoint(@PathVariable("id") Long colId){

        List<Package> packages = colManagerService.getSentPackageInACollectionPoint(colId);
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/{id}/curr-packages")
    @PreAuthorize("hasRole('HEADCOL')")
    public ResponseEntity<List<Package>> getCurrPackagesInATransactionPoint(@PathVariable("id") Long colId){
        List<Package> packages = colManagerService.getCurrPackageInACollectionPoint(colId);
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/{id}/rec-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getReceivePackageInACollectionPoint(@PathVariable("id") Long colId){
        List<Package> packages = colManagerService.getReceivePackageInACollectionPoint(colId);
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }

}
