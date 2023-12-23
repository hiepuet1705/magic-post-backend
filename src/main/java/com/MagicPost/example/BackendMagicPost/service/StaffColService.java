package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCT;

public interface StaffColService {
    public String confirmPackageFromTransactionPoint(Long deliveryReceiptTCId);

    public String confirmPackageFromOtherCollectionPoint(Long deliveryReceiptCCId);

    public DeliveryReceiptCC createDeliveryReceiptCC(DeliveryReceiptCC deliveryReceiptCC,
                                                     Long collectionPointSenderId,
                                                     Long collectionPointReceiverId,
                                                     Long packageId);

    public DeliveryReceiptCT createDeliveryReceiptCT(DeliveryReceiptCT deliveryReceiptCT,
                                                     Long collectionPointSenderId,
                                                     Long transactionPointReceiverId,
                                                     Long packageId);


}
