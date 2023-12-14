package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryReceiptTCRepository extends JpaRepository<DeliveryReceiptTC,Long> {
    public List<DeliveryReceiptTC> getDeliveryRReceiptTCByTransactionPointId(Long TranPointId);
}
