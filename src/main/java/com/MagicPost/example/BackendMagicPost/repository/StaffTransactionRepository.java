package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.StaffCollection;
import com.MagicPost.example.BackendMagicPost.entity.StaffTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffTransactionRepository extends JpaRepository<StaffTransaction,Long>, JpaSpecificationExecutor<StaffTransaction> {
    public StaffTransaction findStaffTransactionByUserId(Long userId);

    @Query("SELECT s FROM StaffTransaction s WHERE s.isManager = true AND s.transactionPoint.id = :tranId")
    public StaffTransaction getStaffByIsManager(@Param("tranId") Long tranId);

    @Query("SELECT s FROM StaffTransaction s WHERE s.user.id = :userId")
    public StaffTransaction getStaffByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM StaffTransaction s WHERE s.transactionPoint.id = :tranId")
    public List<StaffTransaction> getAllStaffByTranId(@Param("tranId") Long tranId);
}
