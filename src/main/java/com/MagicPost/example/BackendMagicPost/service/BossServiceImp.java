package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BossServiceImp implements BossService{
    private CollectionPointRepository collectionPointRepository;
    private TransactionPointRepository transactionPointRepository;

    private StaffTransactionRepository staffTransactionRepository;
    private StaffCollectionRepository staffCollectionRepository;

    private PackageRepository packageRepository;

    @Autowired
    public BossServiceImp(CollectionPointRepository collectionPointRepository,
                          TransactionPointRepository transactionPointRepository,
                          StaffTransactionRepository staffTransactionRepository,
                          StaffCollectionRepository staffCollectionRepository,
                          PackageRepository packageRepository) {
        this.collectionPointRepository = collectionPointRepository;
        this.transactionPointRepository = transactionPointRepository;
        this.staffTransactionRepository = staffTransactionRepository;
        this.staffCollectionRepository = staffCollectionRepository;
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
    public StaffTransaction getManagerOfATranPoint(Long tranId) {
        TransactionPoint transactionPoint =transactionPointRepository.findById(tranId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,
                        "Transaction Point not found"));
        StaffTransaction manager = staffTransactionRepository.getStaffByIsManager(tranId);
        return manager;
    }

    @Override
    public StaffCollection getManagerOfAColPoint(Long tranId) {
        CollectionPoint collectionPoint =collectionPointRepository.findById(tranId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,
                        "Collection Point not found"));
        StaffCollection manager = staffCollectionRepository.getStaffByIsManager(tranId);
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

    @Override
    public List<Package> getPackagesInACollectionPoint(Long colId) {
        CollectionPoint collectionPoint =collectionPointRepository.findById(colId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,
                        "Collection Point not found"));
        List<Package> packages = packageRepository.getPackagesInCollectionPoint(colId);
        return packages;
    }


}
