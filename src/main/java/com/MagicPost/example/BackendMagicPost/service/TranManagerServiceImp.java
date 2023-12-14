package com.MagicPost.example.BackendMagicPost.service;


import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.entity.StaffTransaction;
import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.DeliveryReceiptTCRepository;
import com.MagicPost.example.BackendMagicPost.repository.PackageRepository;
import com.MagicPost.example.BackendMagicPost.repository.StaffTransactionRepository;
import com.MagicPost.example.BackendMagicPost.repository.TransactionPointRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranManagerServiceImp implements TranManagerService {
    private TransactionPointRepository transactionPointRepository;
    private StaffTransactionRepository staffTransactionRepository;

    private PackageRepository packageRepository;

    private DeliveryReceiptTCRepository deliveryReceiptTCRepository;

    public TranManagerServiceImp(TransactionPointRepository transactionPointRepository
            , StaffTransactionRepository staffTransactionRepository,
                                 DeliveryReceiptTCRepository deliveryReceiptTCRepository,
                                 PackageRepository packageRepository) {
        this.transactionPointRepository = transactionPointRepository;
        this.staffTransactionRepository = staffTransactionRepository;
        this.deliveryReceiptTCRepository = deliveryReceiptTCRepository;
        this.packageRepository = packageRepository;
    }

    @Override
    public TransactionPoint getTransactionPointByManagerId(Long managerId) {
        StaffTransaction manager = staffTransactionRepository.findById(managerId).orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,
                "Manager not found"));
        return manager.getTransactionPoint();
    }

    @Override
    public List<Package> getSentPackageInATransactionPoint(Long TranPointId) {
        List<DeliveryReceiptTC> deliveryReceiptTCs =  deliveryReceiptTCRepository.getDeliveryRReceiptTCByTransactionPointId(TranPointId);

        List<Package> packages = deliveryReceiptTCs.stream().map(deliveryReceiptTC -> packageRepository.getPackageByDeReceiptTCId(deliveryReceiptTC.getId())).toList();
        return packages;

    }
}
