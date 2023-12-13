package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.CollectionPoint;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.entity.StaffTransaction;
import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.CollectionPointRepository;
import com.MagicPost.example.BackendMagicPost.repository.PackageRepository;
import com.MagicPost.example.BackendMagicPost.repository.StaffTransactionRepository;
import com.MagicPost.example.BackendMagicPost.repository.TransactionPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BossServiceImp implements BossService{
    private CollectionPointRepository collectionPointRepository;
    private TransactionPointRepository transactionPointRepository;

    private StaffTransactionRepository staffTransactionRepository;

    private PackageRepository packageRepository;

    @Autowired
    public BossServiceImp(CollectionPointRepository collectionPointRepository,
                          TransactionPointRepository transactionPointRepository,
                          StaffTransactionRepository staffTransactionRepository,
                          PackageRepository packageRepository) {
        this.collectionPointRepository = collectionPointRepository;
        this.transactionPointRepository = transactionPointRepository;
        this.staffTransactionRepository = staffTransactionRepository;
        this.packageRepository = packageRepository;
    }

    @Override
    public List<CollectionPoint> getAllCollectionPoints() {
        List<CollectionPoint> collectionPoints =  collectionPointRepository.findAll();
        return collectionPoints;
    }

    @Override
    public List<TransactionPoint> getAllTransactionPoints() {
        return transactionPointRepository.findAll();
    }

    @Override
    public StaffTransaction getManagerOfSTranPoint(Long tranId) {
        TransactionPoint transactionPoint =transactionPointRepository.findById(tranId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,
                        "Transaction Point not found"));
        StaffTransaction manager = staffTransactionRepository.getStaffByIsManager(tranId);
        return manager;
    }

    @Override
    public List<Package> getPackagesInATransactionPoint(Long tranId) {
        TransactionPoint transactionPoint =transactionPointRepository.findById(tranId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,
                        "Transaction Point not found"));
        List<Package> packages = packageRepository.getPackagesInTransactionPoint(tranId);
        return packages;
    }


}
