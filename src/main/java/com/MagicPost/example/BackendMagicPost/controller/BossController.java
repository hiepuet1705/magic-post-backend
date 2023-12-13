package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.CollectionPoint;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.entity.StaffTransaction;
import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;
import com.MagicPost.example.BackendMagicPost.service.BossService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/boss")
public class BossController {
    private BossService bossService;

    public BossController(BossService bossService) {
        this.bossService = bossService;
    }
    @GetMapping("/col-points")
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<List<CollectionPoint>>  getAllCollectionPoints(){
        List<CollectionPoint> collectionPoints = bossService.getAllCollectionPoints();
        return new ResponseEntity<>(collectionPoints, HttpStatus.OK);
    }
    @GetMapping("/tran-points")
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<List<TransactionPoint>>  getAllTransactionPoints(){
        List<TransactionPoint> transactionPoints = bossService.getAllTransactionPoints();
        return new ResponseEntity<>(transactionPoints, HttpStatus.OK);
    }
    @GetMapping("/tran-point/{id}/manger")
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<StaffTransaction> getManagerOfATransactionPoint(@PathVariable("id") Long tranPointId ){

        StaffTransaction manager =  bossService.getManagerOfSTranPoint(tranPointId);
        return new ResponseEntity<>(manager,HttpStatus.OK);
    }

    @GetMapping("/tran-point/{id}/packages")
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<List<Package>> getPackagesFromATranPoint(@PathVariable("id") Long tranPointId ){

        List<Package> packages = bossService.getPackagesInATransactionPoint(tranPointId);
        return new ResponseEntity<>(packages,HttpStatus.OK);
    }

}
