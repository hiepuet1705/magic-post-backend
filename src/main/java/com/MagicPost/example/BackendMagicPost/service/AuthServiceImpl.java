package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;

import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.payload.CustomerRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.LoginDto;


import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;
import com.MagicPost.example.BackendMagicPost.payload.UserDto;
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

    private UserService userService;

    public AuthServiceImpl(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           RoleRepository roleRepository,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           StaffTransactionRepository staffTransactionRepository,
                           StaffCollectionRepository staffCollectionRepository,
                           TransactionPointRepository transactionPointRepository,
                           CollectionPointRepository collectionPointRepository,
                           UserService userService) {
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
        this.userService = userService;
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
    public UserDto changePassword(UserDto user) {
        return null;
    }


    @Override
    public Customer register(CustomerRegisterDto registerDto) {
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

        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }




}

