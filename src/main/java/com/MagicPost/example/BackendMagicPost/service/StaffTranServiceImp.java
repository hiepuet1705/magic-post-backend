package com.MagicPost.example.BackendMagicPost.service;


import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.CustomerRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.PackageAnCustomerRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.PointDto;
import com.MagicPost.example.BackendMagicPost.repository.*;
import com.MagicPost.example.BackendMagicPost.utils.PackageStatus;
import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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

    private UserRepository userRepository;

    private UserService userService;

    private AuthService authService;

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
                               UserRepository userRepository,
                               UserService userService,
                               PasswordEncoder passwordEncoder,
                               AuthService authService
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
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Override
    public Package createPackage(Package aPackage,String phoneNumber) {
        if(!userRepository.existsByPhoneNumber(phoneNumber)){
            throw new CustomApiException(HttpStatus.BAD_REQUEST,"phone number does not exist");
        }
        User user = userRepository.getUserByPhoneNumber(phoneNumber);

        Customer customer = customerRepository.getCustomerByUserId(user.getId());
        if(customer==null){
            throw new CustomApiException(HttpStatus.BAD_REQUEST,"Customer does not exist");
        }
        //
        Long transactionPointId = getTranPointIdOfCurrentStaff();
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction not found"));


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
    public Package createPackageStrangeCustomer(PackageAnCustomerRegisterDto packageAnCustomerRegisterDto) {
        Package aPackage = new Package();
        aPackage.setName(packageAnCustomerRegisterDto.getName());
        aPackage.setWeight(packageAnCustomerRegisterDto.getWeight());
        aPackage.setType(packageAnCustomerRegisterDto.getType());
        aPackage.setReceiverDistrict(packageAnCustomerRegisterDto.getReceiverDistrict());
        aPackage.setReceiverProvince(packageAnCustomerRegisterDto.getReceiverProvince());
        aPackage.setDescription(packageAnCustomerRegisterDto.getDescription());
        aPackage.setReceiverFirstName(packageAnCustomerRegisterDto.getReceiverFirstName());
        aPackage.setReceiverLastName(packageAnCustomerRegisterDto.getReceiverLastName());
        aPackage.setReceiverPhoneNumber(packageAnCustomerRegisterDto.getReceiverPhoneNumber());
        CustomerRegisterDto customerRegisterDto = packageAnCustomerRegisterDto.getCustomerRegisterDto();
        Long transactionPointId = getTranPointIdOfCurrentStaff();
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId).
                orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction not found"));
        Customer newCustomer = authService.register(customerRegisterDto);
        aPackage.setSender(newCustomer);
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
    public List<Package> getPendingPackageInATransactionPoint() {
        Long currentTranPoint = getTranPointIdOfCurrentStaff();
        List<Package> pendingPackages = packageRepository.getPendingPackageInATransactionPoint(currentTranPoint);
        return pendingPackages;
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
        customerReceipt.setTransactionPointReceive(transactionPoint);
        customerReceipt.setSenderName(customer.getUser().getFirstName()+customer.getUser().getLastName());
        customerReceipt.setSenderPhoneNumber(customer.getUser().getPhoneNumber());
        customerReceipt.setReceiverName(aPackage.getReceiverFirstName() + aPackage.getReceiverLastName());
        customerReceipt.setReceiverPhoneNumber(aPackage.getReceiverPhoneNumber());
        customerReceipt.setHashKey(aPackage.getHashKey());
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
        aPackage.setSentFrom("Transaction Point");
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
    public DeliveryReceiptCT confirmReceiptFromCollectionPoint(Long packageId) {
        DeliveryReceiptCT deliveryReceiptCT = deliveryReceiptCTRepository.getDeliveryReceiptCTByPackageId(packageId);
        if(!deliveryReceiptCT.getTransactionPointReceiver().getId()
                .equals(getTranPointIdOfCurrentStaff())){
            throw  new CustomApiException(HttpStatus.CONFLICT,"Conflict between staff and tranPoint");

        }
        Package aPackage = packageRepository.findById(packageId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Package not found"));
        deliveryReceiptCT.setStatus(ReceiptStatus.TRANSFERED);
        deliveryReceiptCT.setTimeArriveNextPoint(new Date().toString());
        aPackage.setStatus(PackageStatus.AT_TRANSACTION_POINT);
        // Update
        aPackage.setCollectionPoint(0L);
        aPackage.setTransactionPoint(deliveryReceiptCT.getTransactionPointReceiver().getId());

        // save
        packageRepository.save(aPackage);
        DeliveryReceiptCT savedDeliveryReceiptCT = deliveryReceiptCTRepository.save(deliveryReceiptCT);

        return savedDeliveryReceiptCT;


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
    public String confirmShippedToReceiver(Long packageId) {
        DeliveryReceiptToReceiver deliveryReceiptToReceiver = deliveryReceiptToReceiverRepository.
                getDeliveryReceiptToReceiverByPackageId(packageId);
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

//    @Override
//    public byte[] printPdf(Long customerReceiptId) throws DocumentException {
//
//        CustomerReceipt customerReceipt = customerReceiptRepository.findById(customerReceiptId)
//                .orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST,"customer receipt not found"));
//        if(!customerReceipt.getTransactionPointReceive().getId().equals(getTranPointIdOfCurrentStaff())){
//            throw new RuntimeException("Conflict");
//        }
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Document document = new Document();
//        PdfWriter.getInstance(document, baos);
//        document.open();
//
//        document.add(new Paragraph("Name: " + customerReceipt.getName()));
//        document.add(new Paragraph("Description: " + customerReceipt.getDescription()));
//        document.add(new Paragraph("Phone: " + customerReceipt.getReceiverPhoneNumber()));
//
//        document.close();
//
//        return baos.toByteArray();
//    }


}
