package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryReceiptTCRepository extends JpaRepository<DeliveryReceiptTC,Long> {
    @Query("SELECT d FROM DeliveryReceiptTC d WHERE d.transactionPointSender.id = :tranPointId")
    public List<DeliveryReceiptTC> getDeliveryRReceiptTCByTransactionPointId(@Param("tranPointId") Long tranPointId);
}
