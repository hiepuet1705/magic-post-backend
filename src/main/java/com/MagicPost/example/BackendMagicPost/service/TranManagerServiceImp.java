package com.MagicPost.example.BackendMagicPost.service;


import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranManagerServiceImp implements TranManagerService {
    private TransactionPointRepository transactionPointRepository;
    private StaffTransactionRepository staffTransactionRepository;

    private PackageRepository packageRepository;

    private DeliveryReceiptTCRepository deliveryReceiptTCRepository;
    private DeliveryReceiptCTRepository deliveryReceiptCTRepository;

    private CustomerReceiptRepository customerReceiptRepository;

    public TranManagerServiceImp(TransactionPointRepository transactionPointRepository,
                                 StaffTransactionRepository staffTransactionRepository,
                                 DeliveryReceiptTCRepository deliveryReceiptTCRepository,
                                 DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                                 CustomerReceiptRepository customerReceiptRepository,
                                 PackageRepository packageRepository) {
        this.transactionPointRepository = transactionPointRepository;
        this.staffTransactionRepository = staffTransactionRepository;
        this.deliveryReceiptTCRepository = deliveryReceiptTCRepository;
        this.deliveryReceiptCTRepository = deliveryReceiptCTRepository;
        this.customerReceiptRepository = customerReceiptRepository;
        this.packageRepository = packageRepository;
    }

    @Override
    public TransactionPoint getTransactionPointByManagerId(Long managerId) {
        StaffTransaction manager = staffTransactionRepository.findById(managerId).orElseThrow(
                () -> new CustomApiException(HttpStatus.BAD_REQUEST,
                        "Manager not found"));
        return manager.getTransactionPoint();
    }

    @Override
    public List<Package> getSentPackageInATransactionPoint(Long TranPointId) {
        List<DeliveryReceiptTC> deliveryReceiptTCs = deliveryReceiptTCRepository.
                getSentDeliveryReceiptTCByTransactionPointId(TranPointId);


        List<Package> packages = deliveryReceiptTCs.stream().map(deliveryReceiptTC ->
                packageRepository.findById(deliveryReceiptTC.getAPackage().getId())
                        .orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,
                                "Manager not found"))).toList();
        return packages;

    }

    @Override
    public List<Package> getCurrentPackagesInATransactionPoint(Long tranPointId) {
        return packageRepository.getCurrentPackageInATransactionPoint(tranPointId);
    }

    @Override
    public List<Package> getReceivePackagesInATransactionPoint(Long tranPointId) {
        List<DeliveryReceiptCT> deliveryReceiptCTs =
                deliveryReceiptCTRepository.getReceivedDeliveryReceiptCTByCollectionPointId(tranPointId);
        List<CustomerReceipt> customerReceipts = customerReceiptRepository.getCustomerReceiptByTranId(tranPointId);
        List<Long> packageId = new ArrayList<>();
        for(DeliveryReceiptCT de : deliveryReceiptCTs){
            packageId.add(de.getAPackage().getId());
        }
        for(CustomerReceipt cr : customerReceipts){
            packageId.add(cr.getAPackage().getId());
        }
        List<Package> packages = packageId.stream().map(id -> packageRepository.findById(id)
                .orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Package not found"))).toList();
        return packages;

    }
}
