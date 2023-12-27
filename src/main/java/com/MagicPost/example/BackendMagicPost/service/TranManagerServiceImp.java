package com.MagicPost.example.BackendMagicPost.service;


import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.PointDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;
import com.MagicPost.example.BackendMagicPost.payload.UserDto;
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

    private UserService userService;

    public TranManagerServiceImp(TransactionPointRepository transactionPointRepository,
                                 StaffTransactionRepository staffTransactionRepository,
                                 DeliveryReceiptTCRepository deliveryReceiptTCRepository,
                                 DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                                 CustomerReceiptRepository customerReceiptRepository,
                                 UserService userService,
                                 PackageRepository packageRepository) {
        this.transactionPointRepository = transactionPointRepository;
        this.staffTransactionRepository = staffTransactionRepository;
        this.deliveryReceiptTCRepository = deliveryReceiptTCRepository;
        this.deliveryReceiptCTRepository = deliveryReceiptCTRepository;
        this.customerReceiptRepository = customerReceiptRepository;
        this.packageRepository = packageRepository;
        this.userService = userService;
    }

    @Override
    public TransactionPoint getTransactionPointByManagerId(Long managerId) {
        StaffTransaction manager = staffTransactionRepository.findById(managerId).orElseThrow(
                () -> new CustomApiException(HttpStatus.BAD_REQUEST,
                        "Manager not found"));
        return manager.getTransactionPoint();
    }

    @Override
    public List<StaffDto> getAllStaffInATransactionPoint() {
        Long tranPointId = getTranPointIdOfCurrentStaff();
        TransactionPoint transactionPoint = transactionPointRepository.findById(tranPointId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Collection Point not found"));
        List<StaffTransaction> staffTransactions =  staffTransactionRepository.getAllStaffByTranId(tranPointId);
        List<StaffDto> staffDtoList = new ArrayList<>();
        for ( StaffTransaction staffTransaction:staffTransactions ) {
            StaffDto staffDto = new StaffDto();
            staffDto.setId(staffTransaction.getId());
            staffDto.setIsManager(staffTransaction.getIsManager());
            staffDto.setType("Transaction Staff");
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

            //
            PointDto pointDto = new PointDto();
            pointDto.setId(transactionPoint.getId());
            pointDto.setName(transactionPoint.getName());
            pointDto.setDistrict(transactionPoint.getDistrict());
            pointDto.setProvince(transactionPoint.getProvince());
            staffDto.setPointDto(pointDto);
            staffDtoList.add(staffDto);
        }
        return staffDtoList;
    }

    @Override
    public Long getTranPointIdOfCurrentStaff() {
        Long currentUserId = userService.getCurrentUserId();

        StaffTransaction staffTransaction = staffTransactionRepository.getStaffByUserId(currentUserId);

        //
        return staffTransaction.getTransactionPoint().getId();
    }

    @Override
    public List<Package> getSentPackageInATransactionPoint() {
        Long currentTranId = getTranPointIdOfCurrentStaff();
        List<DeliveryReceiptTC> deliveryReceiptTCs = deliveryReceiptTCRepository.
                getSentDeliveryReceiptTCByTransactionPointId(currentTranId);


        List<Package> packages = deliveryReceiptTCs.stream().map(deliveryReceiptTC ->
                packageRepository.findById(deliveryReceiptTC.getAPackage().getId())
                        .orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,
                                "Manager not found"))).toList();
        return packages;

    }

    @Override
    public List<Package> getCurrentPackagesInATransactionPoint() {
        Long currentTranId = getTranPointIdOfCurrentStaff();
        return packageRepository.getCurrentPackageInATransactionPoint(currentTranId);
    }

    @Override
    public List<Package> getReceivePackagesInATransactionPoint() {
        Long currentTranId = getTranPointIdOfCurrentStaff();
        List<DeliveryReceiptCT> deliveryReceiptCTs =
                deliveryReceiptCTRepository.getReceivedDeliveryReceiptCTByTransactionPointId(currentTranId);
        List<CustomerReceipt> customerReceipts = customerReceiptRepository.getCustomerReceiptByTranId(currentTranId);
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
