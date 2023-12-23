package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCT;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ColManagerServiceImp implements ColManagerService{
    private CollectionPointRepository collectionPointRepository;
    private StaffCollectionRepository staffCollectionRepository;

    private PackageRepository packageRepository;



    private DeliveryReceiptCTRepository deliveryReceiptCTRepository;

    private DeliveryReceiptCCRepository deliveryReceiptCCRepository;

    private DeliveryReceiptTCRepository deliveryReceiptTCRepository;

    public ColManagerServiceImp(CollectionPointRepository collectionPointRepository,
                                StaffCollectionRepository staffCollectionRepository,
                                PackageRepository packageRepository,
                                DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                                DeliveryReceiptCCRepository deliveryReceiptCCRepository,
                                DeliveryReceiptTCRepository deliveryReceiptTCRepository) {
        this.collectionPointRepository = collectionPointRepository;
        this.staffCollectionRepository = staffCollectionRepository;
        this.packageRepository = packageRepository;
        this.deliveryReceiptCTRepository = deliveryReceiptCTRepository;
        this.deliveryReceiptCCRepository = deliveryReceiptCCRepository;
        this.deliveryReceiptTCRepository = deliveryReceiptTCRepository;
    }

    @Override
    public List<Package> getSentPackageInACollectionPoint(Long colPointId) {
        List<DeliveryReceiptCT> deliveryReceiptCTList = deliveryReceiptCTRepository.getSentDeliveryReceiptCTByCollectionPointId(colPointId);
        List<DeliveryReceiptCC> deliveryReceiptCCList = deliveryReceiptCCRepository.getSentDeliveryReceiptCCByCollectionPointId(colPointId);
        Set<Long> packageId = new HashSet<>();
        for(DeliveryReceiptCT de : deliveryReceiptCTList){
            packageId.add(de.getAPackage().getId());
        }
        for(DeliveryReceiptCC de : deliveryReceiptCCList){
            packageId.add(de.getAPackage().getId());
        }
        List<Package> packages = packageId.stream().map(id -> packageRepository.findById(id)
                .orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Package not found"))).toList();
        return packages;
    }

    @Override
    public List<Package> getCurrPackageInACollectionPoint(Long colPointId) {
        List<Package> packages = packageRepository.getPackagesInCollectionPoint(colPointId);
        return packages;
    }

    @Override
    public List<Package> getReceivePackageInACollectionPoint(Long colPointId) {
        List<DeliveryReceiptCC> deliveryReceiptCCList = deliveryReceiptCCRepository
                .getDeliveryRReceiptCCByCollectionPointId(colPointId);
        List<DeliveryReceiptTC> deliveryReceiptTCList = deliveryReceiptTCRepository
                .getDeliveryReceiptTCByCollectionPointId(colPointId);

        Set<Long> packageId = new HashSet<>();
        for(DeliveryReceiptCC de : deliveryReceiptCCList){
            packageId.add(de.getAPackage().getId());
        }
        for(DeliveryReceiptTC de : deliveryReceiptTCList){
            packageId.add(de.getAPackage().getId());
        }
        List<Package> packages = packageId.stream().map(id -> packageRepository.findById(id)
                .orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Package not found"))).toList();
        return packages;

    }
}
