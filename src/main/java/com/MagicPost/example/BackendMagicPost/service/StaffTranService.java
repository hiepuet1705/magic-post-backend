package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptToReceiver;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.CustomerRegisterDto;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.Resource;

import java.util.List;

public interface StaffTranService {

    Package createPackage(Package aPackage,Long customerId);

    Package createPackageStrangeCustomer(Package aPackage, CustomerRegisterDto customerRegisterDto);
    public List<Package> getSentPackageInATransactionPoint();

    public List<Package> getCurrentPackagesInATransactionPoint();



    public List<Package> getReceivePackagesInATransactionPoint();
    Long getTranPointIdOfCurrentStaff();
    public CustomerReceipt createCustomerReceipt(Long packageId, CustomerReceipt customerReceiptDto);


    // need to update
    public DeliveryReceiptTC createDeliveryReceiptTC(DeliveryReceiptTC deliveryReceiptTC,
                                                     Long collectionPointId, Long packageId);




    public DeliveryReceiptToReceiver createReceiptToReceiver(DeliveryReceiptToReceiver deliveryReceiptToReceiver,
                                                             Long packageId);
    public String confirmReceiptFromCollectionPoint(Long deliveryCTId);
    public String confirmShippedToReceiver(Long deliveryRToReceiverId);
    public String confirmShippedUncompletedToReceiver(Long deliveryRToReceiverId);

    public List<DeliveryReceiptToReceiver> getAllCompletedPackage(Long tranId);

    CustomerReceipt getSingleCustomerReceipt(Long customerReceiptId);


//    byte[] printPdf(Long customerReceiptId) throws DocumentException;

}
