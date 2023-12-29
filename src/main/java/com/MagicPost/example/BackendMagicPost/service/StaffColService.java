package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCT;
import com.MagicPost.example.BackendMagicPost.entity.Package;

import java.util.List;

public interface StaffColService {
    public String confirmPackageFromTransactionPoint(Long deliveryReceiptTCId);

    Long getColPointIdOfCurrentStaff();

    public String confirmPackageFromOtherCollectionPoint(Long deliveryReceiptCCId);

    public DeliveryReceiptCC createDeliveryReceiptCC(DeliveryReceiptCC deliveryReceiptCC,
                                                     Long collectionPointReceiverId,
                                                     Long packageId);

    public DeliveryReceiptCT createDeliveryReceiptCT(DeliveryReceiptCT deliveryReceiptCT,
                                                     Long transactionPointReceiverId,
                                                     Long packageId);
    List<Package> getSentPackageInACollectionPoint();

    List<Package> getCurrPackageInACollectionPoint();

    List<Package> getReceivePackageInACollectionPoint();


}
