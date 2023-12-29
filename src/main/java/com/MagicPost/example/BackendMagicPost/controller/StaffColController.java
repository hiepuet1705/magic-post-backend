package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCT;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.service.StaffColService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/staff/col")
public class StaffColController {
    private StaffColService staffColService;

    public StaffColController(StaffColService staffColService) {
        this.staffColService = staffColService;
    }
    @PutMapping("/receipt-tc/confirm/{receiptTCId}")
    @PreAuthorize("hasRole('OFFICERCOL')")
    public ResponseEntity<String> confirmReceiptFromTransactionPoint(@PathVariable("receiptTCId") Long receiptTCId
    ){

        String confirm = staffColService.confirmPackageFromTransactionPoint(receiptTCId);
        return new ResponseEntity<>(confirm, HttpStatus.OK);

    }
    @PutMapping("/receipt-cc/confirm/{receiptCCId}")
    @PreAuthorize("hasRole('OFFICERCOL')")
    public ResponseEntity<String> confirmReceiptFromOtherCollectionPoint(@PathVariable("receiptCCId")
                                                                             Long receiptCCId
    ){

        String confirm = staffColService.confirmPackageFromOtherCollectionPoint(receiptCCId);
        return new ResponseEntity<>(confirm, HttpStatus.OK);

    }
    @PostMapping("/receipt-cc/{packageId}/{colReceiveId}")
    @PreAuthorize("hasRole('OFFICERCOL')")
    public ResponseEntity<DeliveryReceiptCC> createDeliveryReceiptCC(@RequestBody DeliveryReceiptCC deliveryReceiptCC,
                                                                     @PathVariable("packageId") Long packageId,
                                                                     @PathVariable("colReceiveId") Long collectionReceiveId) {

        DeliveryReceiptCC responseDeliveryReceiptCC =  staffColService.createDeliveryReceiptCC(deliveryReceiptCC,
                collectionReceiveId,packageId);
        return new ResponseEntity<>(responseDeliveryReceiptCC, HttpStatus.OK);
    }

    @PostMapping("/receipt-ct/{packageId}/{tranReceiveId}")
    @PreAuthorize("hasRole('OFFICERCOL')")
    public ResponseEntity<DeliveryReceiptCT> createDeliveryReceiptCT(@RequestBody DeliveryReceiptCT deliveryReceiptCT,
                                                                     @PathVariable("packageId") Long packageId,
                                                                     @PathVariable("tranReceiveId") Long transactionReceiveId) {

        DeliveryReceiptCT responseDeliveryReceiptCT =  staffColService.createDeliveryReceiptCT(deliveryReceiptCT,
                transactionReceiveId,packageId);
        return new ResponseEntity<>(responseDeliveryReceiptCT, HttpStatus.OK);
    }

    @GetMapping("/sent-packages")
    @PreAuthorize("hasRole('OFFICERCOL')")
    public ResponseEntity<List<Package>> getSentPackagesInACollectionPoint(){

        List<Package> packages = staffColService.getSentPackageInACollectionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }

    @GetMapping("/curr-packages")
    @PreAuthorize("hasRole('OFFICERCOL')")
    public ResponseEntity<List<Package>> getCurrPackagesInACollectionPoint(){
        List<Package> packages = staffColService.getCurrPackageInACollectionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/rec-packages")
    @PreAuthorize("hasRole('OFFICERCOL')")
    public ResponseEntity<List<Package>> getReceivePackageInACollectionPoint(){
        List<Package> packages = staffColService.getReceivePackageInACollectionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/pending-packages")
    @PreAuthorize("hasRole('OFFICERCOL')")
    public ResponseEntity<List<Package>> getPendingPackagesInAColPoint(){
        List<Package> packages = staffColService.getPendingPackageInACollectionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }

}
