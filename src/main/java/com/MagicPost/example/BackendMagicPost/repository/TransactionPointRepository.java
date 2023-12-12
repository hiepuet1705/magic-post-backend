package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionPointRepository extends JpaRepository<TransactionPoint,Long> {

}
