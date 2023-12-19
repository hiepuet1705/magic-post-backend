package com.MagicPost.example.BackendMagicPost.service;


import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.*;
import com.MagicPost.example.BackendMagicPost.utils.PackageStatus;
import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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


    public StaffTranServiceImp(StaffTransactionRepository staffTransactionRepository,
                                CustomerRepository customerRepository,
                               CustomerReceiptRepository customerReceiptRepository,
                               CollectionPointRepository collectionPointRepository,
                               DeliveryReceiptTCRepository deliveryReceiptTCRepository,
                               TransactionPointRepository transactionPointRepository,
                               DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                               DeliveryReceiptToReceiverRepository deliveryReceiptToReceiverRepository,
                               PackageRepository packageRepository
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
    }

    @Override
    public CustomerReceipt createCustomerReceipt(Long CustomerId, CustomerReceipt customerReceipt) {
        Customer customer = customerRepository.findById(CustomerId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Customer not found"));
        customerReceipt.setCustomerSender(customer);
        customerReceipt.setSenderName(customer.getFirstName()+customer.getLastName());
        customerReceipt.setSenderPhoneNumber(customer.getPhoneNumber());
        CustomerReceipt customerReceipt1 =  customerReceiptRepository.save(customerReceipt);
        return customerReceipt1;
    }

    @Override
    public DeliveryReceiptTC createDeliveryReceiptTC(DeliveryReceiptTC deliveryReceiptTC, Long collectionPointId, Long packageId, Long transactionPointId) {
        CollectionPoint collectionPoint = collectionPointRepository.findById(collectionPointId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Collection Point not found"));
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId).
               orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction Point Not Found"));
        Package aPackage = packageRepository.findById(packageId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Package Not found"));
        aPackage.setStatus(PackageStatus.TRANSFERING);

        deliveryReceiptTC.setSentPointAddress(transactionPoint.getAddress());
        deliveryReceiptTC.setReceivePointAddress(collectionPoint.getAddress());
        deliveryReceiptTC.setPackageName(aPackage.getName());
        deliveryReceiptTC.setTransactionPointSender(transactionPoint);
        deliveryReceiptTC.setCollectionPointReceiver(collectionPoint);
        deliveryReceiptTC.setAPackage(aPackage);
        deliveryReceiptTC.setStatus(ReceiptStatus.TRANSFERED);




        DeliveryReceiptTC savedDeliveryReceiptTC =  deliveryReceiptTCRepository.save(deliveryReceiptTC);
        return savedDeliveryReceiptTC;
    }

    @Override
    public String confirmReceiptFromCollectionPoint(Long deliveryCTId, Long packageId) {
        Package aPackage = packageRepository.findById(packageId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Package not found"));
        aPackage.setStatus(PackageStatus.AT_TRANSACTION_POINT);
        DeliveryReceiptCT deliveryReceiptCT = deliveryReceiptCTRepository.findById(deliveryCTId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"ReceiptCT not found"));
        deliveryReceiptCT.setStatus(ReceiptStatus.CURR_HERE);

        // save
        packageRepository.save(aPackage);
        deliveryReceiptCTRepository.save(deliveryReceiptCT);

        return "Confirm Receipt From Collection Point";


    }

    @Override
    public DeliveryReceiptToReceiver createReceiptToReceiver(DeliveryReceiptToReceiver deliveryReceiptToReceiver,
                                                             Long transactionPointId, Long packageId) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction Point Not Found"));
        Package aPackage = packageRepository.findById(packageId).
                orElseThrow(()->new CustomApiException(HttpStatus.BAD_REQUEST,"Package Not found"));
        aPackage.setStatus(PackageStatus.TRANSFERING);
        deliveryReceiptToReceiver.setAPackage(aPackage);
        deliveryReceiptToReceiver.setTransactionPointSender(transactionPoint);
        deliveryReceiptToReceiver.setSentPointAddress(transactionPoint.getAddress());
        deliveryReceiptToReceiver.setPackageName(aPackage.getName());
        DeliveryReceiptToReceiver savedReceipt = deliveryReceiptToReceiverRepository.save(deliveryReceiptToReceiver);
        return savedReceipt;
    }

    @Override
    public String confirmShippedToReceiver(Long deliveryRToReceiverId) {
        DeliveryReceiptToReceiver deliveryReceiptToReceiver = deliveryReceiptToReceiverRepository.findById(deliveryRToReceiverId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"receipt to receiver not found"));
        deliveryReceiptToReceiver.setStatus(ReceiptStatus.TRANSFERED);
        deliveryReceiptToReceiverRepository.save(deliveryReceiptToReceiver);
        return "Successfully transfer to Receiver";
    }

    @Override
    public String confirmShippedUncompletedToReceiver(Long deliveryRToReceiverId) {
        DeliveryReceiptToReceiver deliveryReceiptToReceiver = deliveryReceiptToReceiverRepository.findById(deliveryRToReceiverId)
                .orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,"Receipt to receiver not found"));
        if(deliveryReceiptToReceiver.getStatus().equals(ReceiptStatus.TRANSFERED) ){
            deliveryReceiptToReceiver.setStatus(ReceiptStatus.UNCOMPLETED);
            deliveryReceiptToReceiverRepository.save(deliveryReceiptToReceiver);
            return "Incompleted transfer to Receiver ";
        }
        return "Cannot confirm Incompleted";
    }

    @Override
    public List<DeliveryReceiptToReceiver> getAllCompletedPackage(Long tranId) {
            List<DeliveryReceiptToReceiver> deliveryReceiptToReceivers =
                    deliveryReceiptToReceiverRepository.getAllCompletedDeliveryReceiptToReceiverByTranId(tranId);
            return deliveryReceiptToReceivers;


    }


}
