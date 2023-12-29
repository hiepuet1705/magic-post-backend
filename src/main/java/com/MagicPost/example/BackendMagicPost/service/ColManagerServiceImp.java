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
public class ColManagerServiceImp implements ColManagerService{
    private CollectionPointRepository collectionPointRepository;
    private StaffCollectionRepository staffCollectionRepository;

    private PackageRepository packageRepository;

    private UserService userService;



    private DeliveryReceiptCTRepository deliveryReceiptCTRepository;

    private DeliveryReceiptCCRepository deliveryReceiptCCRepository;

    private DeliveryReceiptTCRepository deliveryReceiptTCRepository;

    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public ColManagerServiceImp(CollectionPointRepository collectionPointRepository,
                                StaffCollectionRepository staffCollectionRepository,
                                PackageRepository packageRepository,
                                DeliveryReceiptCTRepository deliveryReceiptCTRepository,
                                DeliveryReceiptCCRepository deliveryReceiptCCRepository,
                                DeliveryReceiptTCRepository deliveryReceiptTCRepository,
                                UserService userService,
                                RoleRepository roleRepository,
                                PasswordEncoder passwordEncoder) {
        this.collectionPointRepository = collectionPointRepository;
        this.staffCollectionRepository = staffCollectionRepository;
        this.packageRepository = packageRepository;
        this.deliveryReceiptCTRepository = deliveryReceiptCTRepository;
        this.deliveryReceiptCCRepository = deliveryReceiptCCRepository;
        this.deliveryReceiptTCRepository = deliveryReceiptTCRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<StaffDto> getAllStaffInACollectionPoint() {
        Long colPointId = getColPointIdOfCurrentStaff();
        CollectionPoint collectionPoint = collectionPointRepository.findById(colPointId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Collection Point not found"));
        List<StaffCollection> staffCollections =  staffCollectionRepository.getAllStaffByColId(colPointId);
        List<StaffDto> staffDtoList = new ArrayList<>();
        for ( StaffCollection staffCollection:staffCollections ) {
            StaffDto staffDto = new StaffDto();
            staffDto.setId(staffCollection.getId());
            staffDto.setIsManager(staffCollection.getIsManager());
            staffDto.setType("Collection Staff");
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

            //
            PointDto pointDto = new PointDto();
            pointDto.setId(collectionPoint.getId());
            pointDto.setName(collectionPoint.getName());
            pointDto.setDistrict(collectionPoint.getDistrict());
            pointDto.setProvince(collectionPoint.getProvince());
            staffDto.setPointDto(pointDto);
            staffDtoList.add(staffDto);
        }
        return staffDtoList;
    }



    @Override
    public Long getColPointIdOfCurrentStaff() {
        Long currentUserId = userService.getCurrentUserId();
        // find Staff find userId;
        StaffCollection staffCollection = staffCollectionRepository.getStaffByUserId(currentUserId);

        //
        return staffCollection.getCollectionPoint().getId();
    }

    @Override
    public String createAccountForStaffCol(StaffRegisterDto staffRegisterDto) {
        Long currentColId = getColPointIdOfCurrentStaff();
        CollectionPoint collectionPoint = collectionPointRepository.findById(currentColId)
                .orElseThrow(() -> new CustomApiException(HttpStatus.BAD_REQUEST, "Collection Point not found"));
        StaffCollection staffCollection = new StaffCollection();
        staffCollection.setCollectionPoint(collectionPoint);

        User userStaff = new User();
        userStaff.setFirstName(staffRegisterDto.getFirstName());
        userStaff.setLastName(staffRegisterDto.getLastName());
        userStaff.setPhoneNumber(staffRegisterDto.getPhoneNumber());
        userStaff.setAddress(staffRegisterDto.getAddress());
        userStaff.setUsername(staffRegisterDto.getUsername());
        userStaff.setPassword(passwordEncoder.encode(staffRegisterDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(Constant.roleStaffCol).get();
        roles.add(userRole);
        userStaff.setRoles(roles);
        staffCollection.setUser(userStaff);
        staffCollectionRepository.save(staffCollection);

        return "staff Col create successfully";
    }
}
