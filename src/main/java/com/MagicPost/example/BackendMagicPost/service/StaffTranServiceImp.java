package com.MagicPost.example.BackendMagicPost.service;


import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.PointDto;
import com.MagicPost.example.BackendMagicPost.repository.*;
import com.MagicPost.example.BackendMagicPost.utils.PackageStatus;
import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StaffTranServiceImp implements StaffTranService {

    private StaffTransactionRepository staffTransactionRepository;
    private CustomerRepository customerRepository;

    private CustomerReceiptRepository customerReceiptRepository;

    private CollectionPointRepository collectionPointRepository;

    private TransactionPointRepository transactionPointRepository;
    private DeliveryReceiptTCRepository deliveryReceiptTCRepository;

    private PackageRepository packageRepository;

    private DeliveryReceiptCTRepository deliveryReceiptCTRepository;

    private DeliveryReceiptToReceiverRepository deliveryReceiptToReceiverRepository;

    private UserService userService;

    private PasswordEncoder passwordEncoder;


    public StaffTranServiceImp(StaffTransactionRepository staffTransactionRepository,
                                CustomerRepository customerRepository,
                               CustomerReceiptRepository customerReceiptRepository,
                               CollectionPointRepository collectionPointRepository,
                               DeliveryReceiptTCRepository deliveryReceiptTCRepository,
                               TransactionPointRepository transactionPointRepository,
                               DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                               DeliveryReceiptToReceiverRepository deliveryReceiptToReceiverRepository,
                               PackageRepository packageRepository,
                               UserService userService,
                               PasswordEncoder passwordEncoder
                                ) {
        this.staffTransactionRepository = staffTransactionRepository;
        this.customerRepository = customerRepository;
        this.customerReceiptRepository = customerReceiptRepository;
        this.deliveryReceiptTCRepository = deliveryReceiptTCRepository;
        this.transactionPointRepository = transactionPointRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.deliveryReceiptCTRepository = deliveryReceiptCTRepository;
        this.packageRepository = packageRepository;
        this.deliveryReceiptToReceiverRepository = deliveryReceiptToReceiverRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Package createPackage(Package aPackage,Long customerId) {
        //
        Long transactionPointId = getTranPointIdOfCurrentStaff();
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction not found"));

        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,"Customer not found"));
        aPackage.setSender(customer);
        aPackage.setTransactionPoint(transactionPointId);
        aPackage.setCollectionPoint(0L);





        aPackage.setStatus(PackageStatus.AT_TRANSACTION_POINT);
        String pkKey =  passwordEncoder.encode
                (aPackage.getName() + aPackage.getId()).replaceAll("[$./]", "").substring(0,12);
        aPackage.setHashKey(pkKey);
        Package savedPackage = packageRepository.save(aPackage);
        return savedPackage;
    }

    @Override
    public Long getTranPointIdOfCurrentStaff() {
        Long currentUserId = userService.getCurrentUserId();

        StaffTransaction staffTransaction = staffTransactionRepository.getStaffByUserId(currentUserId);

        //
        return staffTransaction.getTransactionPoint().getId();
    }

    @Override
    public CustomerReceipt createCustomerReceipt(Long packageId, CustomerReceipt customerReceipt) {


        Package aPackage = packageRepository.findById(packageId).
                orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"package not found"));
        Customer customer = aPackage.getSender();
        Long tranPointId = getTranPointIdOfCurrentStaff();
        TransactionPoint transactionPoint = transactionPointRepository.findById(tranPointId).get();
        customerReceipt.setCustomerSender(customer);
        customerReceipt.setAPackage(aPackage);
        customerReceipt.setName(aPackage.getName());
        customerReceipt.setDescription(aPackage.getDescription());
        customerReceipt.setTime(new Date().toString());
        customerReceipt.setTransactionPointReceive(transactionPoint);
        customerReceipt.setSenderName(customer.getUser().getFirstName()+customer.getUser().getLastName());
        customerReceipt.setSenderPhoneNumber(customer.getUser().getPhoneNumber());
        customerReceipt.setReceiverName(aPackage.getReceiverFirstName() + aPackage.getReceiverLastName());
        customerReceipt.setReceiverPhoneNumber(aPackage.getReceiverPhoneNumber());
        CustomerReceipt customerReceipt1 =  customerReceiptRepository.save(customerReceipt);
        return customerReceipt1;
    }

    // More CreatePackage

    @Override
    public DeliveryReceiptTC createDeliveryReceiptTC(DeliveryReceiptTC deliveryReceiptTC,
                                                     Long collectionPointId, Long packageId) {
        Long currentStaffTranPoint = getTranPointIdOfCurrentStaff();
        CollectionPoint collectionPoint = collectionPointRepository.findById(collectionPointId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Collection Point not found"));
        TransactionPoint transactionPoint = transactionPointRepository.findById(currentStaffTranPoint).
               orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction Point Not Found"));
        Package aPackage = packageRepository.findById(packageId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Package Not found"));

        // Update package
        aPackage.setStatus(PackageStatus.TRANSFERING);

        //
        aPackage.setCollectionPoint(collectionPointId);
        aPackage.setTransactionPoint(0L);

        deliveryReceiptTC.setSentPointAddress(transactionPoint.getDistrict() + " " + transactionPoint.getProvince());
        deliveryReceiptTC.setReceivePointAddress(collectionPoint.getDistrict() + " " + collectionPoint.getProvince());
        deliveryReceiptTC.setPackageName(aPackage.getName());
        deliveryReceiptTC.setTransactionPointSender(transactionPoint);
        deliveryReceiptTC.setCollectionPointReceiver(collectionPoint);
        deliveryReceiptTC.setAPackage(aPackage);
        deliveryReceiptTC.setReceiverName(aPackage.getReceiverFirstName() + aPackage.getReceiverLastName());
        deliveryReceiptTC.setStatus(ReceiptStatus.TRANSFERING);
        deliveryReceiptTC.setType(aPackage.getType());
        DeliveryReceiptTC savedDeliveryReceiptTC =  deliveryReceiptTCRepository.save(deliveryReceiptTC);
        return savedDeliveryReceiptTC;
    }

    @Override
    public String confirmReceiptFromCollectionPoint(Long deliveryCTId) {

        DeliveryReceiptCT deliveryReceiptCT = deliveryReceiptCTRepository.findById(deliveryCTId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"ReceiptCT not found"));
        if(!deliveryReceiptCT.getTransactionPointReceiver().getId()
                .equals(getTranPointIdOfCurrentStaff())){
            throw  new CustomApiException(HttpStatus.CONFLICT,"Conflict between staff and tranPoint");

        }
        Long packageId = deliveryReceiptCT.getAPackage().getId();
        Package aPackage = packageRepository.findById(packageId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Package not found"));

        deliveryReceiptCT.setStatus(ReceiptStatus.TRANSFERED);
//        deliveryReceiptCT.setTime(new Date().toString());

        aPackage.setStatus(PackageStatus.AT_TRANSACTION_POINT);

        // Update
        aPackage.setCollectionPoint(0L);
        aPackage.setTransactionPoint(deliveryReceiptCT.getTransactionPointReceiver().getId());

        // save
        packageRepository.save(aPackage);
        deliveryReceiptCTRepository.save(deliveryReceiptCT);

        return "Confirm Receipt From Collection Point with PackageId = " + packageId ;


    }

    @Override
    public DeliveryReceiptToReceiver createReceiptToReceiver(DeliveryReceiptToReceiver deliveryReceiptToReceiver,
                                                              Long packageId) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(getTranPointIdOfCurrentStaff()).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction Point Not Found"));
        Package aPackage = packageRepository.findById(packageId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Package Not found"));
        aPackage.setStatus(PackageStatus.TRANSFERING);

        aPackage.setCollectionPoint(0L);
        aPackage.setTransactionPoint(0L);


        deliveryReceiptToReceiver.setAPackage(aPackage);
        deliveryReceiptToReceiver.setStatus(ReceiptStatus.TRANSFERING);
        deliveryReceiptToReceiver.setTransactionPointSender(transactionPoint);
        deliveryReceiptToReceiver.setSentPointAddress(transactionPoint.getDistrict() + " " + transactionPoint.getProvince());
        deliveryReceiptToReceiver.setType(aPackage.getType());
        deliveryReceiptToReceiver.setReceiverName(aPackage.getReceiverFirstName()+aPackage.getReceiverLastName());
        deliveryReceiptToReceiver.setReceiverPhoneNumber(aPackage.getReceiverPhoneNumber());
        deliveryReceiptToReceiver.setPackageName(aPackage.getName());
        DeliveryReceiptToReceiver savedReceipt = deliveryReceiptToReceiverRepository.save(deliveryReceiptToReceiver);
        return savedReceipt;
    }

    @Override
    public String confirmShippedToReceiver(Long deliveryRToReceiverId) {
        DeliveryReceiptToReceiver deliveryReceiptToReceiver = deliveryReceiptToReceiverRepository.findById(deliveryRToReceiverId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"receipt to receiver not found"));
        deliveryReceiptToReceiver.setStatus(ReceiptStatus.TRANSFERED);
        // Update Status Of Package
        Package aPackage =  deliveryReceiptToReceiver.getAPackage();
        aPackage.setStatus(PackageStatus.SHIP_DONE);
        aPackage.setCollectionPoint(0L);
        aPackage.setTransactionPoint(0L);
        packageRepository.save(aPackage);
        deliveryReceiptToReceiverRepository.save(deliveryReceiptToReceiver);

        return "Successfully transfer to Receiver ";
    }

    @Override
    public String confirmShippedUncompletedToReceiver(Long deliveryRToReceiverId) {
        DeliveryReceiptToReceiver deliveryReceiptToReceiver = deliveryReceiptToReceiverRepository.findById(deliveryRToReceiverId)
                .orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,"Receipt to receiver not found"));
        deliveryReceiptToReceiver.setStatus(ReceiptStatus.UNCOMPLETED);
        Package aPackage =  deliveryReceiptToReceiver.getAPackage();
        aPackage.setStatus(PackageStatus.RETURNED_TO_TRANSACTION_POINT);
        aPackage.setCollectionPoint(0L);
        aPackage.setTransactionPoint(deliveryReceiptToReceiver.getTransactionPointSender().getId());

        packageRepository.save(aPackage);
        deliveryReceiptToReceiverRepository.save(deliveryReceiptToReceiver);
        return "Package is returned to transaction Point";

    }

    @Override
    public List<DeliveryReceiptToReceiver> getAllCompletedPackage(Long tranId) {
            List<DeliveryReceiptToReceiver> deliveryReceiptToReceivers =
                    deliveryReceiptToReceiverRepository.getAllCompletedDeliveryReceiptToReceiverByTranId(tranId);
            return deliveryReceiptToReceivers;


    }

    @Override
    public CustomerReceipt getSingleCustomerReceipt(Long customerReceiptId) {
        return customerReceiptRepository.findById(customerReceiptId).get();
    }


}
