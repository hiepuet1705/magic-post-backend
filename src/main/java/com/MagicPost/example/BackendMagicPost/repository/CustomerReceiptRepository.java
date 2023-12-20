package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReceiptRepository extends JpaRepository<CustomerReceipt,Long> {
}
