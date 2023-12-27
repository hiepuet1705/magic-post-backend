package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.PackageFlowDto;
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
    public PackageFlowDto trackingSinglePackage(Long packageId) {
        // find receipt
        Long currentUserId = userService.getCurrentUserId();
        Package aPackage = packageRepository.
                findById(packageId).orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST, "Package not found"));
        if (!aPackage.getSender().getUser().getId().equals(currentUserId)) {
            throw new CustomApiException(HttpStatus.CONFLICT, "Conflict between customer and packageId");
        }
        PackageFlowDto packageFlowDto = new PackageFlowDto();
        packageFlowDto.setPackageId(packageId);
        packageFlowDto.setStatus(aPackage.getStatus());
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
                packageFlowDto.setCurrentPoint(pointDto);
            } else if (aPackage.getTransactionPoint() != 0L) {
                TransactionPoint transactionPoint = transactionPointRepository.
                        findById(aPackage.getTransactionPoint()).
                        orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST, "Transaction not found"));
                pointDto.setId(transactionPoint.getId());
                pointDto.setName(transactionPoint.getName());
                pointDto.setDistrict(transactionPoint.getDistrict());
                pointDto.setProvince(transactionPoint.getProvince());
                packageFlowDto.setCurrentPoint(pointDto);
            }
        }
        CustomerReceipt customerReceipt = customerReceiptRepository.getCustomerReceiptByPackageId(packageId);
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
        packageFlowDto.setPointHistoryDtoList(pointDtos);

        if(customerReceipt!= null){
            packageFlowDto.setFirstTranPoint(firstPointDto);
            packageFlowDto.setFirstTranPointStatus(customerReceipt.getStatus());
        }
        DeliveryReceiptTC deliveryReceiptTC = deliveryReceiptTCRepository.getDeliveryReceiptTCByPackageId(packageId);
        if(deliveryReceiptTC!= null){
            packageFlowDto.setFirstColPoint(secondPointDto);
            packageFlowDto.setFirstColPointStatus(deliveryReceiptTC.getStatus());

        }
        else {
            packageFlowDto.setFirstColPoint(secondPointDto);
        }
        //set point
        packageFlowDto.setSecondColPoint(thirdPointDto);
        packageFlowDto.setSecondTranPoint(fourthPointDto);
        if(packageFlowDto.getFirstColPointStatus().equals(ReceiptStatus.TRANSFERED)){
            DeliveryReceiptCC deliveryReceiptCC = deliveryReceiptCCRepository.getDeliveryReceiptCCByPackageId(packageId);
            if(deliveryReceiptCC!= null){

                packageFlowDto.setSecondColPointStatus(deliveryReceiptCC.getStatus());

            }
        }
        if(packageFlowDto.getSecondColPointStatus().equals(ReceiptStatus.TRANSFERED)){
            DeliveryReceiptCT deliveryReceiptCT = deliveryReceiptCTRepository.getDeliveryReceiptCTByPackageId(packageId);
            if(deliveryReceiptCT!= null){
                packageFlowDto.setSecondTranPointStatus(deliveryReceiptCT.getStatus());
            }
        }
        return packageFlowDto;
    }
}
