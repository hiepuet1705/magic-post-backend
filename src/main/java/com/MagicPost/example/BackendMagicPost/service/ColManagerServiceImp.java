package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCT;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColManagerServiceImp implements ColManagerService{
    private CollectionPointRepository collectionPointRepository;
    private StaffCollectionRepository staffCollectionRepository;

    private PackageRepository packageRepository;



    private DeliveryReceiptCTRepository deliveryReceiptCTRepository;

    private DeliveryReceiptCCRepository deliveryReceiptCCRepository;

    public ColManagerServiceImp(CollectionPointRepository collectionPointRepository,
                                StaffCollectionRepository staffCollectionRepository,
                                PackageRepository packageRepository,
                                DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                                DeliveryReceiptCCRepository deliveryReceiptCCRepository) {
        this.collectionPointRepository = collectionPointRepository;
        this.staffCollectionRepository = staffCollectionRepository;
        this.packageRepository = packageRepository;
        this.deliveryReceiptCTRepository = deliveryReceiptCTRepository;
        this.deliveryReceiptCCRepository = deliveryReceiptCCRepository;
    }

    @Override
    public List<Package> getSentPackageInACollectionPoint(Long colPointId) {
        List<DeliveryReceiptCT> deliveryReceiptCTList = deliveryReceiptCTRepository.getSentDeliveryReceiptCTByCollectionPointId(colPointId);
        List<DeliveryReceiptCC> deliveryReceiptCCList = deliveryReceiptCCRepository.getSentDeliveryReceiptCCByCollectionPointId(colPointId);
        List<Long> packageId = new ArrayList<>();
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
        return null;
    }

    @Override
    public List<Package> getReceivePackageInACollectionPoint(Long colPointId) {
        return null;
    }
}
