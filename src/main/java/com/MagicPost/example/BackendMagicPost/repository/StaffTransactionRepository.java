package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.StaffTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffTransactionRepository extends JpaRepository<StaffTransaction,Long> {
}
