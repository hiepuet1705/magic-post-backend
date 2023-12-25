package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;

import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.CustomerRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.LoginDto;


import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;
import com.MagicPost.example.BackendMagicPost.repository.*;
import com.MagicPost.example.BackendMagicPost.security.JwtTokenProvider;
import com.MagicPost.example.BackendMagicPost.utils.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    //private AuthenticationManager
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private CustomerRepository customerRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;
    private StaffTransactionRepository staffTransactionRepository;

    private StaffCollectionRepository staffCollectionRepository;

    private TransactionPointRepository transactionPointRepository;

    private CollectionPointRepository collectionPointRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           RoleRepository roleRepository,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           StaffTransactionRepository staffTransactionRepository,
                           StaffCollectionRepository staffCollectionRepository,
                           TransactionPointRepository transactionPointRepository,
                           CollectionPointRepository collectionPointRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerRepository = customerRepository;
        this.staffTransactionRepository = staffTransactionRepository;
        this.staffCollectionRepository = staffCollectionRepository;
        this.transactionPointRepository = transactionPointRepository;
        this.collectionPointRepository = collectionPointRepository;
    }

    //

    @Override
    public String login(LoginDto loginDto) {
        // find user ... Load by username
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword()));
        // check
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return token;

    }

    @Override
    public String  register(CustomerRegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new CustomApiException(HttpStatus.BAD_REQUEST, "Username is already exists");
        }

        User user = new User();
        user.setAddress(registerDto.getAddress());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPhoneNumber(registerDto.getPhoneNumber());

        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(Constant.roleCustomer).get();
        roles.add(userRole);
        user.setRoles(roles);

        Customer customer = new Customer();
        customer.setUser(user);

        customerRepository.save(customer);
        return "User register successfully";
    }

    @Override
    public String createAccountForStaffTran(StaffRegisterDto staffRegisterDto) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(staffRegisterDto.getPointId())
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

    @Override
    public String createAccountForStaffCol(StaffRegisterDto staffRegisterDto) {
        CollectionPoint collectionPoint = collectionPointRepository.findById(staffRegisterDto.getPointId())
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

