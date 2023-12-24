package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptToReceiver;
import com.MagicPost.example.BackendMagicPost.entity.Package;

import java.util.List;

public interface StaffTranService {

    Package createPackage(Package aPackage,Long customerId,Long tranId);
    public CustomerReceipt createCustomerReceipt(Long CustomerId,Long packageId,Long tranId, CustomerReceipt customerReceiptDto);


    // need to update
    public DeliveryReceiptTC createDeliveryReceiptTC(DeliveryReceiptTC deliveryReceiptTC,
                                                     Long collectionPointId, Long packageId, Long transactionPointId);


    public String confirmReceiptFromCollectionPoint(Long deliveryCTId);

    public DeliveryReceiptToReceiver createReceiptToReceiver(DeliveryReceiptToReceiver deliveryReceiptToReceiver,
                                                             Long transactionPointId,
                                                             Long packageId
                        );
    public String confirmShippedToReceiver(Long deliveryRToReceiverId);
    public String confirmShippedUncompletedToReceiver(Long deliveryRToReceiverId);

    public List<DeliveryReceiptToReceiver> getAllCompletedPackage(Long tranId);

}
