package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.CustomerRegisterDto;
import com.MagicPost.example.BackendMagicPost.service.StaffTranService;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/staff/tran")
public class StaffTranController {
    private StaffTranService staffTranService;

    public StaffTranController(StaffTranService staffTranService) {
        this.staffTranService = staffTranService;
    }

    @PostMapping("/package/{customerId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<Package> createPackage(@RequestBody Package aPackage,
                                                 @PathVariable("customerId") Long customerId
    ) {
        Package savedPackage = staffTranService.createPackage(aPackage, customerId);
        return new ResponseEntity<>(savedPackage, HttpStatus.OK);

    }

    @PostMapping("/package/stranger")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<Package> createPackageStrangeCustomer(@RequestBody Package aPackage,
                                                                @RequestBody CustomerRegisterDto customerRegisterDto
    ) {
        Package savedPackage = staffTranService.createPackageStrangeCustomer(aPackage, customerRegisterDto);
        return new ResponseEntity<>(savedPackage, HttpStatus.OK);

    }

    @PostMapping("/customer-receipt/{packageId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<CustomerReceipt> createCustomerReceipt(@PathVariable("packageId") Long packageId,
                                                                 @RequestBody CustomerReceipt customerReceipt) {
        CustomerReceipt responseCustomerReceipt = staffTranService.createCustomerReceipt(packageId, customerReceipt);
        return new ResponseEntity<>(responseCustomerReceipt, HttpStatus.OK);
    }

    @PostMapping("/receipt-tc/{colId}/{packageId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<DeliveryReceiptTC> createDeliveryReceiptTC(@RequestBody DeliveryReceiptTC deliveryReceiptDto,
                                                                     @PathVariable("colId") Long collectionId,
                                                                     @PathVariable("packageId") Long packageId) {

        DeliveryReceiptTC responseDeliveryReceiptTC = staffTranService.createDeliveryReceiptTC(deliveryReceiptDto,
                collectionId, packageId);
        return new ResponseEntity<>(responseDeliveryReceiptTC, HttpStatus.OK);
    }

    @PostMapping("/receipt-to-receiver/{packageId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<DeliveryReceiptToReceiver> createReceiptToReceiver(@RequestBody DeliveryReceiptToReceiver deliveryReceiptToReceiver,
                                                                             @PathVariable("packageId") Long packageId
    ) {


        DeliveryReceiptToReceiver savedPackage = staffTranService.createReceiptToReceiver(deliveryReceiptToReceiver, packageId);
        return new ResponseEntity<>(savedPackage, HttpStatus.OK);

    }

    @PutMapping("/receipt-ct/confirm/{packageId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<DeliveryReceiptCT> confirmReceiptFromCollectionPoint(@PathVariable("packageId") Long packageId) {
        DeliveryReceiptCT deliveryReceiptCT = staffTranService.confirmReceiptFromCollectionPoint(packageId);
        return new ResponseEntity<>(deliveryReceiptCT, HttpStatus.OK);

    }

    @PutMapping("/receipt-receiver/confirm/{packageId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<String> confirmShippedToReceiver(@PathVariable("packageId") Long packageId) {

        String confirm = staffTranService.confirmShippedToReceiver(packageId);
        return new ResponseEntity<>(confirm, HttpStatus.OK);

    }

    @PutMapping("/receipt-receiver/confirm/unsuccessful/{deliveryRToReceiverId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<String> confirmShippedUncompletedToReceiver(@PathVariable("deliveryRToReceiverId") Long deliveryRToReceiverId
    ) {

        String confirm = staffTranService.confirmShippedUncompletedToReceiver(deliveryRToReceiverId);
        return new ResponseEntity<>(confirm, HttpStatus.OK);

    }

    @GetMapping("/tran-id")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<Long> getTranIdOfCurrentUser() {
        Long tranId = staffTranService.getTranPointIdOfCurrentStaff();
        return new ResponseEntity<>(tranId, HttpStatus.OK);
    }


    @GetMapping("/completed-packages/{tranId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<List<DeliveryReceiptToReceiver>> getAllCompletedPackage(@PathVariable("tranId") Long tranId) {

        List<DeliveryReceiptToReceiver> completedDeliveryReceiptToReceivers =
                staffTranService.getAllCompletedPackage(tranId);

        return new ResponseEntity<>(completedDeliveryReceiptToReceivers, HttpStatus.OK);
    }

    @GetMapping("/sent-packages")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<List<Package>> getSentPackagesInATransactionPoint() {

        List<Package> packages = staffTranService.getSentPackageInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }

    @GetMapping("/curr-packages")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<List<Package>> getCurrPackagesInATransactionPoint() {
        List<Package> packages = staffTranService.getCurrentPackagesInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }

    @GetMapping("/rec-packages")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<List<Package>> getReceivePackagesInATransactionPoint() {
        List<Package> packages = staffTranService.getReceivePackagesInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }

    @GetMapping("/pending-packages")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<List<Package>> getPendingPackagesInATransactionPoint() {
        List<Package> packages = staffTranService.getPendingPackageInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }


}
