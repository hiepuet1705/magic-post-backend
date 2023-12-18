package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;

public interface StaffTranService {
    public CustomerReceipt createCustomerReceipt(Long CustomerId, CustomerReceipt customerReceiptDto);

    public DeliveryReceiptTC createDeliveryReceiptTC(DeliveryReceiptTC deliveryReceiptTC,
                                                     Long collectionPointId, Long packageId, Long transactionPointId);

}
