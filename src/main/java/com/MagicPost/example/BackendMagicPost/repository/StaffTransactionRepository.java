package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.StaffCollection;
import com.MagicPost.example.BackendMagicPost.entity.StaffTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffTransactionRepository extends JpaRepository<StaffTransaction,Long> {
    @Query("SELECT s FROM StaffTransaction s WHERE s.isManager = true AND s.transactionPoint.id = :tranId")
    public StaffTransaction getStaffByIsManager(@Param("tranId") Long tranId);

    @Query("SELECT s FROM StaffTransaction s WHERE s.user.id = :userId")
    public StaffTransaction getStaffByUserId(@Param("userId") Long userId);
}
