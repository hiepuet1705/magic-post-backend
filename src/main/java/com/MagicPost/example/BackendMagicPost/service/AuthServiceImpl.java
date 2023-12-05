package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;

import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.CustomerRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.LoginDto;


import com.MagicPost.example.BackendMagicPost.payload.StaffTranRegisterDto;
import com.MagicPost.example.BackendMagicPost.repository.*;
import com.MagicPost.example.BackendMagicPost.security.JwtTokenProvider;
import com.MagicPost.example.BackendMagicPost.utils.Constant;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
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

    public AuthServiceImpl(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           RoleRepository roleRepository,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           StaffTransactionRepository staffTransactionRepository,
                           StaffCollectionRepository staffCollectionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerRepository = customerRepository;
        this.staffTransactionRepository = staffTransactionRepository;
        this.staffCollectionRepository = staffCollectionRepository;
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

        // map
//        User user = new User();
//        user.setUsername(registerDto.getUsername());
//        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//        Set<Role> roles = new HashSet<>();
//        Role userRole = roleRepository.findByName(Constant.roleCustomer).get();
//        roles.add(userRole);
//        user.setRoles(roles);
        Customer customer = new Customer();
        customer.setAddress(registerDto.getAddress());
        customer.setFirstName(registerDto.getFirstName());
        customer.setLastName(registerDto.getLastName());
        customer.setPhoneNumber(registerDto.getPhoneNumber());
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(Constant.roleCustomer).get();
        roles.add(userRole);
        user.setRoles(roles);
        customer.setUser(user);


        customerRepository.save(customer);
        return "User register successfully";
    }


    @Override
    public String createAccountForStaffTran(StaffTranRegisterDto staffRegisterDto) {

        StaffTransaction staffTransaction = new StaffTransaction();
        staffTransaction.setName(staffRegisterDto.getName());
        User userStaff = new User();
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
    public String createAccountForStaffCol(StaffTranRegisterDto staffRegisterDto) {
        StaffCollection staffCollection = new StaffCollection();
        staffCollection.setName(staffRegisterDto.getName());
        User userStaff = new User();
        userStaff.setUsername(staffRegisterDto.getUsername());
        userStaff.setPassword(passwordEncoder.encode(staffRegisterDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(Constant.roleStaffCol).get();
        roles.add(userRole);
        userStaff.setRoles(roles);
        staffCollection.setUser(userStaff);
        staffCollectionRepository.save(staffCollection);

        return "staff trans create successfully";
    }

}

