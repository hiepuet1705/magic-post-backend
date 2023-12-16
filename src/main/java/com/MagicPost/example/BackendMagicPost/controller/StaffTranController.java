package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import com.MagicPost.example.BackendMagicPost.service.StaffTranService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

}
