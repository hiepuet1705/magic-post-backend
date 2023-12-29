package com.MagicPost.example.BackendMagicPost.service;


import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.PointDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.UserDto;
import com.MagicPost.example.BackendMagicPost.repository.*;
import com.MagicPost.example.BackendMagicPost.utils.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public TranManagerServiceImp(TransactionPointRepository transactionPointRepository,
                                 StaffTransactionRepository staffTransactionRepository,
                                 DeliveryReceiptTCRepository deliveryReceiptTCRepository,
                                 DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                                 CustomerReceiptRepository customerReceiptRepository,
                                 UserService userService,
                                 PackageRepository packageRepository,
                                 PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository) {
        this.transactionPointRepository = transactionPointRepository;
        this.staffTransactionRepository = staffTransactionRepository;
        this.deliveryReceiptTCRepository = deliveryReceiptTCRepository;
        this.deliveryReceiptCTRepository = deliveryReceiptCTRepository;
        this.customerReceiptRepository = customerReceiptRepository;
        this.packageRepository = packageRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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
    public String createAccountForStaffTran(StaffRegisterDto staffRegisterDto) {
        Long currentTranId = getTranPointIdOfCurrentStaff();
        TransactionPoint transactionPoint = transactionPointRepository.findById(currentTranId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Transaction Point Not Found"));
        StaffTransaction staffTransaction = new StaffTransaction();
        staffTransaction.setTransactionPoint(transactionPoint);
        // User
        User userStaff = new User();
        userStaff.setFirstName(staffRegisterDto.getFirstName());
        userStaff.setLastName(staffRegisterDto.getLastName());
        userStaff.setPhoneNumber(staffRegisterDto.getPhoneNumber());
        userStaff.setAddress(staffRegisterDto.getAddress());
        userStaff.setUsername(staffRegisterDto.getUsername());
        userStaff.setPassword(passwordEncoder.encode(staffRegisterDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(Constant.roleStaffTran).get();
        roles.add(userRole);
        userStaff.setRoles(roles);
        staffTransaction.setUser(userStaff);
        staffTransactionRepository.save(staffTransaction);

        return "staff trans create successfully";
    }
}
