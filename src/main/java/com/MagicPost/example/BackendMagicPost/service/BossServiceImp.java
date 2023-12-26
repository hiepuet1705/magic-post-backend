package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.PointDto;
import com.MagicPost.example.BackendMagicPost.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BossServiceImp implements BossService{
    private CollectionPointRepository collectionPointRepository;
    private TransactionPointRepository transactionPointRepository;
    private StaffTransactionRepository staffTransactionRepository;
    private StaffCollectionRepository staffCollectionRepository;
    private PackageRepository packageRepository;

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
    public List<PointDto> getAllCollectionPoints() {
        List<CollectionPoint> collectionPoints =  collectionPointRepository.findAll();
        List<PointDto> pointDtos = new ArrayList<>();
        for(CollectionPoint cl : collectionPoints){
            PointDto pointDto = new PointDto();
            pointDto.setId(cl.getId());
            pointDto.setName(cl.getName());
            pointDto.setAddress(cl.getAddress());
            pointDtos.add(pointDto);
        }

        return pointDtos;
    }

    @Override
    public List<PointDto> getAllTransactionPoints() {
        List<TransactionPoint> transactionPoints =  transactionPointRepository.findAll();
        List<PointDto> pointDtos = new ArrayList<>();
        for(TransactionPoint transactionPoint : transactionPoints){
            PointDto pointDto = new PointDto();
            pointDto.setId(transactionPoint.getId());
            pointDto.setName(transactionPoint.getName());
            pointDto.setAddress(transactionPoint.getAddress());
            pointDtos.add(pointDto);
        }

        return pointDtos;
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
        CollectionPoint collectionPoint = collectionPointRepository.findById(colId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,
                        "Collection Point not found"));
        List<Package> packages = packageRepository.getPackagesInCollectionPoint(colId);
        return packages;
    }

    @Override
    public List<StaffTransaction> getStaffFromATransactionPoint(Long tranId) {
            TransactionPoint transactionPoint = transactionPointRepository.findById(tranId).
                    orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction Point not found"));
            List<StaffTransaction> listStaff = transactionPoint.getStaffTransactions().stream().toList();
            return listStaff;
    }

    @Override
    public List<StaffCollection> getStaffFromACollectionPoint(Long colId) {
        CollectionPoint collectionPoint = collectionPointRepository.findById(colId).
                orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Collection Point not found"));
        List<StaffCollection> listStaff = collectionPoint.getStaffCollections().stream().toList();
        return listStaff;
    }
}
