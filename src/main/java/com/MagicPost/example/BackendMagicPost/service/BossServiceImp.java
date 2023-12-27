package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.PackageDto;
import com.MagicPost.example.BackendMagicPost.payload.PointDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;
import com.MagicPost.example.BackendMagicPost.payload.UserDto;
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
            pointDto.setDistrict(cl.getDistrict());
            pointDto.setProvince(cl.getProvince());
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
            pointDto.setDistrict(transactionPoint.getDistrict());
            pointDto.setProvince(transactionPoint.getProvince());
            pointDtos.add(pointDto);
        }

        return pointDtos;
    }

    @Override
    public List<PackageDto> getAllPackages() {
        List<Package> packages = packageRepository.findAll();
        List<PackageDto> packageDtoList = new ArrayList<>();
        for(Package aPackage : packages){
            PackageDto packageDto = new PackageDto();
            packageDto.setId(aPackage.getId());
            packageDto.setWeight(aPackage.getWeight());
            packageDto.setName(aPackage.getName());
            packageDto.setDescription(aPackage.getDescription());
            packageDto.setType(aPackage.getType());
            packageDto.setStatus(aPackage.getStatus());
            packageDto.setReceiverFirstName(aPackage.getReceiverFirstName());
            packageDto.setReceiverLastName(aPackage.getReceiverLastName());
            packageDto.setReceiverDistrict(aPackage.getReceiverDistrict());
            packageDto.setReceiverProvince(aPackage.getReceiverProvince());
            packageDto.setReceiverPhoneNumber(aPackage.getReceiverPhoneNumber());
            packageDto.setHashKey(aPackage.getHashKey());
            packageDto.setCollectionPoint(aPackage.getCollectionPoint());
            packageDto.setTransactionPoint(aPackage.getTransactionPoint());

            UserDto userDto = new UserDto();
            User user = aPackage.getSender().getUser();
            userDto.setId(user.getId());
            userDto.setLastName(user.getLastName());
            userDto.setFirstName(user.getFirstName());
            userDto.setAddress(user.getAddress());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            packageDto.setUserDto(userDto);
            PointDto pointDto = new PointDto();
            if(aPackage.getCollectionPoint()!=0L){

                CollectionPoint collectionPoint = collectionPointRepository.
                        findById(aPackage.getCollectionPoint()).
                        orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Collection not found"));
                pointDto.setId(collectionPoint.getId());
                pointDto.setName(collectionPoint.getName());
                pointDto.setDistrict(collectionPoint.getDistrict());
                pointDto.setProvince(collectionPoint.getProvince());
            }
            else if(aPackage.getTransactionPoint()!=0L) {
                TransactionPoint transactionPoint = transactionPointRepository.
                        findById(aPackage.getTransactionPoint()).
                        orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction not found"));
                pointDto.setId(transactionPoint.getId());
                pointDto.setName(transactionPoint.getName());
                pointDto.setDistrict(transactionPoint.getDistrict());
                pointDto.setProvince(transactionPoint.getProvince());
            }
            packageDto.setPointDto(pointDto);
            packageDtoList.add(packageDto);


        }
        return packageDtoList;
    }

    @Override
    public List<StaffDto> getAllStaff() {
        List<StaffTransaction> staffTransactions = staffTransactionRepository.findAll();
        List<StaffCollection> staffCollections = staffCollectionRepository.findAll();

        List<StaffDto> allStaff = new ArrayList<>();

        for(StaffTransaction staffTransaction : staffTransactions){
            StaffDto staffDto = new StaffDto();
            staffDto.setId(staffTransaction.getId());
            staffDto.setIsManager(staffTransaction.getIsManager());
            staffDto.setType("Staff Transaction");
            
            // Set User DTO
            User user = staffTransaction.getUser();
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setLastName(user.getLastName());
            userDto.setFirstName(user.getFirstName());
            userDto.setAddress(user.getAddress());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setUsername(user.getUsername());    
            userDto.setPassword(user.getPassword());
            staffDto.setUserDto(userDto);
            // set PointDTO
            TransactionPoint transactionPoint = staffTransaction.getTransactionPoint();
            PointDto pointDto = new PointDto();
            pointDto.setId(transactionPoint.getId());
            pointDto.setName(transactionPoint.getName());
            pointDto.setDistrict(transactionPoint.getDistrict());
            pointDto.setProvince(transactionPoint.getProvince());
            staffDto.setPointDto(pointDto);
            allStaff.add(staffDto);
        }
        for(StaffCollection staffCollection : staffCollections){
            StaffDto staffDto = new StaffDto();
            staffDto.setId(staffCollection.getId());
            staffDto.setIsManager(staffCollection.getIsManager());
            staffDto.setType("Staff Collection");

            // Set User DTO
            User user = staffCollection.getUser();
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setLastName(user.getLastName());
            userDto.setFirstName(user.getFirstName());
            userDto.setAddress(user.getAddress());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            staffDto.setUserDto(userDto);
            // set PointDTO
            CollectionPoint collectionPoint = staffCollection.getCollectionPoint();
            PointDto pointDto = new PointDto();
            pointDto.setId(collectionPoint.getId());
            pointDto.setName(collectionPoint.getName());
            pointDto.setDistrict(collectionPoint.getDistrict());
            pointDto.setProvince(collectionPoint.getProvince());
            staffDto.setPointDto(pointDto);
            allStaff.add(staffDto);
        }
        return allStaff;
        
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
