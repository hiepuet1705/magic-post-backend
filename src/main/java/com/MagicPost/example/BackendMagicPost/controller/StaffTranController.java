package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptToReceiver;
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

    @PostMapping("/customer-receipt/{id}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<CustomerReceipt> createCustomerReceipt(@PathVariable("id") Long CustomerId,
                                                                 @RequestBody CustomerReceipt customerReceipt) {
            CustomerReceipt responseCustomerReceipt =  staffTranService.createCustomerReceipt(CustomerId,customerReceipt);
            return new ResponseEntity<>(responseCustomerReceipt, HttpStatus.OK);
    }

    @PostMapping("/receipt-tc/{colId}/{packageId}/{tranId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<DeliveryReceiptTC> createDeliveryReceiptTC(@RequestBody DeliveryReceiptTC deliveryReceiptDto,
                                                                     @PathVariable("colId") Long collectionId,
                                                                     @PathVariable("packageId") Long packageId,
                                                                     @PathVariable("tranId") Long transactionId) {
        System.out.println("Oke");
        DeliveryReceiptTC responseDeliveryReceiptTC =  staffTranService.createDeliveryReceiptTC(deliveryReceiptDto,
                collectionId,packageId,transactionId);
        return new ResponseEntity<>(responseDeliveryReceiptTC, HttpStatus.OK);
    }

    @PutMapping("/receipt-ct/confirm/{receiptCTId}/packageId")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<String> confirmReceiptFromCollectionPoint(@PathVariable("receiptCTId") Long receiptTCId,
                                                                    @PathVariable Long packageId){

        String confirm = staffTranService.confirmReceiptFromCollectionPoint(receiptTCId, packageId);
        return new ResponseEntity<>(confirm,HttpStatus.OK);

    }

        @GetMapping("/completed-packages/{tranId}")
    @PreAuthorize("hasRole('OFFICERTRAN')")
    public ResponseEntity<List<DeliveryReceiptToReceiver>> getAllCompletedPackage(@PathVariable("tranId") Long tranId){
            System.out.println("Okeeeeeeeeeeeeeee");
        List<DeliveryReceiptToReceiver> completedDeliveryReceiptToReceivers =
                staffTranService.getAllCompletedPackage(tranId);
            System.out.printf("Okieeeeeeeeeeeeeeeee");
        return new ResponseEntity<>(completedDeliveryReceiptToReceivers,HttpStatus.OK);
    }


}
