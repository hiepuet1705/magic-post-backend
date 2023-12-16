package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;

public interface StaffTranService {
    public CustomerReceipt createCustomerReceipt(Long CustomerId, CustomerReceipt customerReceiptDto);
}
