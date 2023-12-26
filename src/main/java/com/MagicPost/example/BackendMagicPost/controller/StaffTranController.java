package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptToReceiver;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.service.StaffTranService;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/customer-receipt/{customerId}/{packageId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<CustomerReceipt> createCustomerReceipt(@PathVariable("customerId") Long CustomerId,
                                                                 @PathVariable("packageId") Long packageId,
                                                                 @RequestBody CustomerReceipt customerReceipt) {
            CustomerReceipt responseCustomerReceipt =  staffTranService.createCustomerReceipt(CustomerId,packageId, customerReceipt);
            return new ResponseEntity<>(responseCustomerReceipt, HttpStatus.OK);
    }

    @PostMapping("/receipt-tc/{colId}/{packageId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<DeliveryReceiptTC> createDeliveryReceiptTC(@RequestBody DeliveryReceiptTC deliveryReceiptDto,
                                                                     @PathVariable("colId") Long collectionId,
                                                                     @PathVariable("packageId") Long packageId) {

        DeliveryReceiptTC responseDeliveryReceiptTC =  staffTranService.createDeliveryReceiptTC(deliveryReceiptDto,
                collectionId,packageId);
        return new ResponseEntity<>(responseDeliveryReceiptTC, HttpStatus.OK);
    }
    @PostMapping("/package/{customerId}/")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<Package> createPackage(@RequestBody Package aPackage,
                                                 @PathVariable("customerId") Long customerId
                                                 ) {


            Package savedPackage = staffTranService.createPackage(aPackage,customerId);
            return new ResponseEntity<>(savedPackage,HttpStatus.OK);

    }
    @PostMapping("/receipt-to-receiver/{packageId}/")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<DeliveryReceiptToReceiver> createReceiptToReceiver(@RequestBody DeliveryReceiptToReceiver deliveryReceiptToReceiver,
                                                 @PathVariable("packageId") Long packageId
    ) {


        DeliveryReceiptToReceiver savedPackage = staffTranService.createReceiptToReceiver(deliveryReceiptToReceiver,packageId);
        return new ResponseEntity<>(savedPackage,HttpStatus.OK);

    }

    @PutMapping("/receipt-ct/confirm/{receiptCTId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<String> confirmReceiptFromCollectionPoint(@PathVariable("receiptCTId") Long receiptCTId
                                                                    ){

        String confirm = staffTranService.confirmReceiptFromCollectionPoint(receiptCTId);
        return new ResponseEntity<>(confirm,HttpStatus.OK);

    }
    @GetMapping("/tran-id}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<Long> getTranIdOfCurrentUser(){
        Long tranId = staffTranService.getTranPointIdOfCurrentStaff();
        return new ResponseEntity<>(tranId,HttpStatus.OK);
    }


    @GetMapping("/completed-packages/{tranId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<List<DeliveryReceiptToReceiver>> getAllCompletedPackage(@PathVariable("tranId") Long tranId){

        List<DeliveryReceiptToReceiver> completedDeliveryReceiptToReceivers =
                staffTranService.getAllCompletedPackage(tranId);

        return new ResponseEntity<>(completedDeliveryReceiptToReceivers,HttpStatus.OK);
    }


}
