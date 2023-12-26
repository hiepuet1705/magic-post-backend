package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptToReceiver;
import com.MagicPost.example.BackendMagicPost.entity.Package;

import java.util.List;

public interface StaffTranService {

    Package createPackage(Package aPackage,Long customerId);

    Long getTranPointIdOfCurrentStaff();
    public CustomerReceipt createCustomerReceipt(Long CustomerId,Long packageId, CustomerReceipt customerReceiptDto);


    // need to update
    public DeliveryReceiptTC createDeliveryReceiptTC(DeliveryReceiptTC deliveryReceiptTC,
                                                     Long collectionPointId, Long packageId);




    public DeliveryReceiptToReceiver createReceiptToReceiver(DeliveryReceiptToReceiver deliveryReceiptToReceiver,
                                                             Long packageId);
    public String confirmReceiptFromCollectionPoint(Long deliveryCTId);
    public String confirmShippedToReceiver(Long deliveryRToReceiverId);
    public String confirmShippedUncompletedToReceiver(Long deliveryRToReceiverId);

    public List<DeliveryReceiptToReceiver> getAllCompletedPackage(Long tranId);

}
