package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.CustomerReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerReceiptRepository extends JpaRepository<CustomerReceipt,Long> {


    @Query("Select cr from CustomerReceipt cr where cr.transactionPointReceive.id =:tranId")
    public List<CustomerReceipt> getCustomerReceiptByTranId(@Param("tranId") Long tranId);
}
