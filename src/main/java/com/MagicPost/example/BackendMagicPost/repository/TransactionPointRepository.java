package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionPointRepository extends JpaRepository<TransactionPoint,Long> {
//    public TransactionPoint getTransactionPointByStaffId(Long staffId);

    @Query("select tp from TransactionPoint tp where tp.district =:district ")
    public TransactionPoint getTransactionPointByDistrict(@Param("district") String district);

}
