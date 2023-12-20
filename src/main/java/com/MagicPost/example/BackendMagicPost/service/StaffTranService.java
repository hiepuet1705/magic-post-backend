package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptToReceiver;
import com.MagicPost.example.BackendMagicPost.entity.Package;

import java.util.List;

public interface StaffTranService {
    public CustomerReceipt createCustomerReceipt(Long CustomerId, CustomerReceipt customerReceiptDto);

    public DeliveryReceiptTC createDeliveryReceiptTC(DeliveryReceiptTC deliveryReceiptTC,
                                                     Long collectionPointId, Long packageId, Long transactionPointId);


    public String confirmReceiptFromCollectionPoint(Long deliveryCTId,Long packageId);

    public DeliveryReceiptToReceiver createReceiptToReceiver(DeliveryReceiptToReceiver deliveryReceiptToReceiver,
                                                             Long transactionPointId,
                                                             Long packageId
                        );
    public String confirmShippedToReceiver(Long deliveryRToReceiverId);
    public String confirmShippedUncompletedToReceiver(Long deliveryRToReceiverId);

    public List<DeliveryReceiptToReceiver> getAllCompletedPackage(Long tranId);

}
