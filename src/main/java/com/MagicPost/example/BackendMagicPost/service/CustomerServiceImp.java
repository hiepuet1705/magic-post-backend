package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.PackageInfoDto;
import com.MagicPost.example.BackendMagicPost.payload.PointDto;
import com.MagicPost.example.BackendMagicPost.repository.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.PackageRepository;
import com.MagicPost.example.BackendMagicPost.utils.PackageStatus;
import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {
    private PackageRepository packageRepository;

    private UserService userService;


    private CustomerRepository customerRepository;

    private CustomerReceiptRepository customerReceiptRepository;

    private DeliveryReceiptTCRepository deliveryReceiptTCRepository;

    private DeliveryReceiptCCRepository deliveryReceiptCCRepository;

    private DeliveryReceiptCTRepository deliveryReceiptCTRepository;

    private TransactionPointRepository transactionPointRepository;

    private CollectionPointRepository collectionPointRepository;

    public CustomerServiceImp(PackageRepository packageRepository,
                              UserService userService,
                              CustomerRepository customerRepository,
                              CustomerReceiptRepository customerReceiptRepository,
                              DeliveryReceiptTCRepository deliveryReceiptTCRepository,
                              DeliveryReceiptCCRepository deliveryReceiptCCRepository,
                              DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                              TransactionPointRepository transactionPointRepository,
                              CollectionPointRepository collectionPointRepository) {
        this.packageRepository = packageRepository;
        this.userService = userService;
        this.customerRepository = customerRepository;
        this.customerReceiptRepository = customerReceiptRepository;
        this.deliveryReceiptTCRepository = deliveryReceiptTCRepository;
        this.deliveryReceiptCCRepository = deliveryReceiptCCRepository;
        this.deliveryReceiptCTRepository = deliveryReceiptCTRepository;
        this.transactionPointRepository = transactionPointRepository;
        this.collectionPointRepository = collectionPointRepository;

    }

    @Override
    public List<Package> getAllPackagesByCustomerId(Long customerId) {

        List<Package> packages = packageRepository.getPackagesByCustomerId(customerId);


        return packages;
    }
    @Override
    public PackageInfoDto trackingSinglePackage(String packageIdHash) {
        // find receipt
        Long currentUserId = userService.getCurrentUserId();
        Package aPackage = packageRepository.getPackageByHashId(packageIdHash);
        if (!aPackage.getSender().getUser().getId().equals(currentUserId)) {
            throw new CustomApiException(HttpStatus.CONFLICT, "Conflict between customer and packageId");
        }
        PackageInfoDto packageInfoDto = new PackageInfoDto();
        packageInfoDto.setPackageId(aPackage.getId());
        packageInfoDto.setStatus(aPackage.getStatus());
        packageInfoDto.setName(aPackage.getName());
        packageInfoDto.setWeight(aPackage.getWeight());
        packageInfoDto.setDescription(aPackage.getDescription());
        packageInfoDto.setType(aPackage.getType());
        packageInfoDto.setReceiverFirstName(aPackage.getReceiverFirstName());
        packageInfoDto.setReceiverLastName(aPackage.getReceiverLastName());
        packageInfoDto.setReceiverProvince(aPackage.getReceiverProvince());
        packageInfoDto.setReceiverDistrict(aPackage.getReceiverDistrict());
        packageInfoDto.setReceiverPhoneNumber(aPackage.getReceiverPhoneNumber());
        packageInfoDto.setHashKey(aPackage.getHashKey());

        if (aPackage.getStatus().equals(PackageStatus.SHIP_DONE) ||
                !aPackage.getStatus().equals(PackageStatus.RETURNED_TO_TRANSACTION_POINT)) {
            PointDto pointDto = new PointDto();
            if (aPackage.getCollectionPoint() != 0L) {

                CollectionPoint collectionPoint = collectionPointRepository.
                        findById(aPackage.getCollectionPoint()).
                        orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST, "Collection not found"));
                pointDto.setId(collectionPoint.getId());
                pointDto.setName(collectionPoint.getName());
                pointDto.setDistrict(collectionPoint.getDistrict());
                pointDto.setProvince(collectionPoint.getProvince());
                packageInfoDto.setCurrentPoint(pointDto);
            } else if (aPackage.getTransactionPoint() != 0L) {
                TransactionPoint transactionPoint = transactionPointRepository.
                        findById(aPackage.getTransactionPoint()).
                        orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST, "Transaction not found"));
                pointDto.setId(transactionPoint.getId());
                pointDto.setName(transactionPoint.getName());
                pointDto.setDistrict(transactionPoint.getDistrict());
                pointDto.setProvince(transactionPoint.getProvince());
                packageInfoDto.setCurrentPoint(pointDto);
            }
        }
        CustomerReceipt customerReceipt = customerReceiptRepository.getCustomerReceiptByPackageId(aPackage.getId());
        if(customerReceipt==null){
            throw new CustomApiException(HttpStatus.BAD_REQUEST,"Haven't create customer receipt yet");
        }
        //
        TransactionPoint firstTranPoint = customerReceipt.getTransactionPointReceive();
        CollectionPoint firstCollectionPoint = collectionPointRepository.getCollectionPointByProvince(firstTranPoint.getProvince());
        CollectionPoint secondColPoint = collectionPointRepository.getCollectionPointByProvince(aPackage.getReceiverProvince());
        TransactionPoint secondTranPoint = transactionPointRepository.getTransactionPointByDistrict(aPackage.getReceiverDistrict());
        PointDto firstPointDto = new PointDto();
        firstPointDto.setId(firstTranPoint.getId());
        firstPointDto.setName(firstTranPoint.getName());
        firstPointDto.setProvince(firstTranPoint.getProvince());
        firstPointDto.setDistrict(firstTranPoint.getDistrict());

        PointDto secondPointDto = new PointDto();
        secondPointDto.setId(firstCollectionPoint.getId());
        secondPointDto.setName(firstCollectionPoint.getName());
        secondPointDto.setProvince(firstCollectionPoint.getProvince());
        secondPointDto.setDistrict(firstCollectionPoint.getDistrict());

        PointDto thirdPointDto = new PointDto();
        thirdPointDto.setId(secondColPoint.getId());
        thirdPointDto.setName(secondColPoint.getName());
        thirdPointDto.setProvince(secondColPoint.getProvince());
        thirdPointDto.setDistrict(secondColPoint.getDistrict());

        PointDto fourthPointDto = new PointDto();
        fourthPointDto.setId(secondTranPoint.getId());
        fourthPointDto.setName(secondTranPoint.getName());
        fourthPointDto.setProvince(secondTranPoint.getProvince());
        fourthPointDto.setDistrict(secondTranPoint.getDistrict());

        List<PointDto> pointDtos = new ArrayList<>();
        pointDtos.add(firstPointDto);
        pointDtos.add(secondPointDto);
        pointDtos.add(thirdPointDto);
        pointDtos.add(fourthPointDto);
        packageInfoDto.setPointHistoryDtoList(pointDtos);

        packageInfoDto.setFirstTranPoint(firstPointDto);
        packageInfoDto.setFirstTranPointStatus(customerReceipt.getStatus());
        packageInfoDto.setTimeArriveFirstPoint(customerReceipt.getTime());
        DeliveryReceiptTC deliveryReceiptTC = deliveryReceiptTCRepository.getDeliveryReceiptTCByPackageId(aPackage.getId());
        if(deliveryReceiptTC!= null){
            packageInfoDto.setFirstColPoint(secondPointDto);
            packageInfoDto.setFirstColPointStatus(deliveryReceiptTC.getStatus());
            if(deliveryReceiptTC.getStatus().equals(ReceiptStatus.TRANSFERED)){
                packageInfoDto.setTimeArriveFirstPoint(deliveryReceiptTC.getTime());
            }

        }
        else {
            packageInfoDto.setFirstColPoint(secondPointDto);
        }
        //set point
        packageInfoDto.setSecondColPoint(thirdPointDto);
        packageInfoDto.setSecondTranPoint(fourthPointDto);
        if(packageInfoDto.getFirstColPointStatus().equals(ReceiptStatus.TRANSFERED)){
            DeliveryReceiptCC deliveryReceiptCC = deliveryReceiptCCRepository.getDeliveryReceiptCCByPackageId(aPackage.getId());
            if(deliveryReceiptCC!= null){

                packageInfoDto.setSecondColPointStatus(deliveryReceiptCC.getStatus());
                packageInfoDto.setTimeArriveSecondColPoint(deliveryReceiptCC.getTime());

            }
        }
        if(packageInfoDto.getSecondColPointStatus().equals(ReceiptStatus.TRANSFERED)){
            DeliveryReceiptCT deliveryReceiptCT = deliveryReceiptCTRepository.getDeliveryReceiptCTByPackageId(aPackage.getId());
            if(deliveryReceiptCT!= null){
                packageInfoDto.setSecondTranPointStatus(deliveryReceiptCT.getStatus());
                packageInfoDto.setTimeArriveSecondTranPoint(deliveryReceiptCT.getTime());
            }
        }
        return packageInfoDto;
    }
}
