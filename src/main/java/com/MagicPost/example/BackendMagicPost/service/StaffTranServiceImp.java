package com.MagicPost.example.BackendMagicPost.service;


import com.MagicPost.example.BackendMagicPost.entity.Customer;
import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.CustomerReceiptRepository;
import com.MagicPost.example.BackendMagicPost.repository.CustomerRepository;
import com.MagicPost.example.BackendMagicPost.repository.StaffTransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StaffTranServiceImp implements StaffTranService {

    private StaffTransactionRepository staffTransactionRepository;
    private CustomerRepository customerRepository;

    private CustomerReceiptRepository customerReceiptRepository;

    public StaffTranServiceImp(StaffTransactionRepository staffTransactionRepository,
                                CustomerRepository customerRepository,
                               CustomerReceiptRepository customerReceiptRepository) {
        this.staffTransactionRepository = staffTransactionRepository;
        this.customerRepository = customerRepository;
        this.customerReceiptRepository = customerReceiptRepository;
    }

    @Override
    public CustomerReceipt createCustomerReceipt(Long CustomerId, CustomerReceipt customerReceipt) {
        Customer customer = customerRepository.findById(CustomerId)
                .orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Customer not found"));
        customerReceipt.setCustomerSender(customer);
        CustomerReceipt customerReceipt1 =  customerReceiptRepository.save(customerReceipt);
        return customerReceipt1;
    }
}
