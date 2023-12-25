package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.*;
import com.MagicPost.example.BackendMagicPost.utils.PackageStatus;
import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StaffColServiceImp implements StaffColService {
    private DeliveryReceiptTCRepository deliveryReceiptTCRepository;

    private PackageRepository packageRepository;

    private DeliveryReceiptCCRepository deliveryReceiptCCRepository;
    private DeliveryReceiptCTRepository deliveryReceiptCTRepository;

    private CollectionPointRepository collectionPointRepository;

    private TransactionPointRepository transactionPointRepository;

    private StaffCollectionRepository staffCollectionRepository;

    private UserService userService;

    public StaffColServiceImp(DeliveryReceiptTCRepository deliveryReceiptTCRepository,
                              PackageRepository packageRepository,
                              DeliveryReceiptCCRepository deliveryReceiptCCRepository,
                              DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                              CollectionPointRepository collectionPointRepository,
                              TransactionPointRepository transactionPointRepository,
                              StaffCollectionRepository staffCollectionRepository,
                              UserService userService) {
        this.deliveryReceiptTCRepository = deliveryReceiptTCRepository;
        this.deliveryReceiptCCRepository = deliveryReceiptCCRepository;
        this.packageRepository = packageRepository;
        this.deliveryReceiptCTRepository = deliveryReceiptCTRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.transactionPointRepository = transactionPointRepository;
        this.staffCollectionRepository = staffCollectionRepository;
        this.userService = userService;

    }


    @Override
    public String confirmPackageFromTransactionPoint(Long deliveryReceiptTCId) {

        // Check current user belong to collection Point or not
//        Long currentUserId = userService.getCurrentUserId();


        // find staff by user
//        StaffCollection staffCollection = staffCollectionRepository.getStaffByUserId(currentUserId);

       // check whether collection point == delivery point

        DeliveryReceiptTC deliveryReceiptTC = deliveryReceiptTCRepository.findById(deliveryReceiptTCId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Delivery Receipt not found"));

//        if(!staffCollection.getCollectionPoint().getId().equals(deliveryReceiptTC.getCollectionPointReceiver().getId())){
//            throw new CustomApiException(HttpStatus.CONFLICT,"Conflict between collection and staff");
//        }
        Long packageId = deliveryReceiptTC.getAPackage().getId();
        Package aPackage = packageRepository.findById(packageId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Package not found"));
        //Update status of receipt and Package
        deliveryReceiptTC.setStatus(ReceiptStatus.TRANSFERED);
        aPackage.setStatus(PackageStatus.AT_COLLECTION_POINT);

        // Update
        aPackage.setTransactionPoint(0L);
        aPackage.setCollectionPoint(deliveryReceiptTC.getCollectionPointReceiver().getId());

        // save
        packageRepository.save(aPackage);
        deliveryReceiptTCRepository.save(deliveryReceiptTC);

        return "Confirm Receipt TC From Transaction Point";
    }

    @Override
    public String confirmPackageFromOtherCollectionPoint(Long deliveryReceiptCCId) {
        DeliveryReceiptCC deliveryReceiptCC = deliveryReceiptCCRepository.findById(deliveryReceiptCCId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Delivery Receipt not found"));
        Long packageId = deliveryReceiptCC.getAPackage().getId();
        Package aPackage = packageRepository.findById(packageId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Package not found"));
        //Update status of receipt and Package
        deliveryReceiptCC.setStatus(ReceiptStatus.TRANSFERED);
        aPackage.setStatus(PackageStatus.AT_COLLECTION_POINT);

        // Update
        aPackage.setTransactionPoint(0L);
        aPackage.setCollectionPoint(deliveryReceiptCC.getCollectionPointReceiver().getId());

        // save
        packageRepository.save(aPackage);
        deliveryReceiptCCRepository.save(deliveryReceiptCC);

        return "Confirm Receipt CC From Other Collection Point";
    }

    @Override
    public DeliveryReceiptCC createDeliveryReceiptCC(DeliveryReceiptCC deliveryReceiptCC,
                                                     Long collectionPointSenderId,
                                                     Long collectionPointReceiverId, Long packageId) {
        CollectionPoint collectionPointSender = collectionPointRepository.findById(collectionPointSenderId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Collection Point not found"));
        CollectionPoint collectionPointReceiver = collectionPointRepository.findById(collectionPointReceiverId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Collection Point Not Found"));
        Package aPackage = packageRepository.findById(packageId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Package Not found"));

        // Update package
        aPackage.setStatus(PackageStatus.TRANSFERING);

        //xac nhan thi moi sua
//        aPackage.setCollectionPoint(collectionPointId);
//        aPackage.setTransactionPoint(0L);

        deliveryReceiptCC.setSentPointAddress(collectionPointSender.getAddress());
        deliveryReceiptCC.setReceivePointAddress(collectionPointReceiver.getAddress());
        deliveryReceiptCC.setPackageName(aPackage.getName());
        deliveryReceiptCC.setReceiverName(aPackage.getReceiverFirstName()+ aPackage.getReceiverLastName());
        deliveryReceiptCC.setCollectionPointSender(collectionPointSender);
        deliveryReceiptCC.setCollectionPointReceiver(collectionPointReceiver);
        deliveryReceiptCC.setAPackage(aPackage);
        deliveryReceiptCC.setType(aPackage.getType());
        deliveryReceiptCC.setStatus(ReceiptStatus.TRANSFERING);
        DeliveryReceiptCC savedDeliveryReceiptCC =  deliveryReceiptCCRepository.save(deliveryReceiptCC);
        return savedDeliveryReceiptCC;
    }

    @Override
    public DeliveryReceiptCT createDeliveryReceiptCT(DeliveryReceiptCT deliveryReceiptCT,
                                                     Long collectionPointSenderId,
                                                     Long transactionPointReceiverId, Long packageId) {
        CollectionPoint collectionPointSender = collectionPointRepository.findById(collectionPointSenderId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Collection Point not found"));
        TransactionPoint transactionPointReceiver = transactionPointRepository.findById(transactionPointReceiverId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction Point Not Found"));
        Package aPackage = packageRepository.findById(packageId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Package Not found"));

        // Update package
        aPackage.setStatus(PackageStatus.TRANSFERING);

        //xac nhan thi moi sua
//        aPackage.setCollectionPoint(collectionPointId);
//        aPackage.setTransactionPoint(0L);

        deliveryReceiptCT.setSentPointAddress(collectionPointSender.getAddress());
        deliveryReceiptCT.setReceivePointAddress(transactionPointReceiver.getAddress());
        deliveryReceiptCT.setPackageName(aPackage.getName());
        deliveryReceiptCT.setCollectionPointSender(collectionPointSender);
        deliveryReceiptCT.setType(aPackage.getType());
        deliveryReceiptCT.setTransactionPointReceiver(transactionPointReceiver);
        deliveryReceiptCT.setReceiverName(aPackage.getReceiverLastName() + aPackage.getReceiverLastName());
        deliveryReceiptCT.setAPackage(aPackage);

        deliveryReceiptCT.setStatus(ReceiptStatus.TRANSFERING);
        DeliveryReceiptCT savedDeliveryReceiptCT =  deliveryReceiptCTRepository.save(deliveryReceiptCT);
        return savedDeliveryReceiptCT;
    }


}
