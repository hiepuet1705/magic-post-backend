package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptToReceiver;
import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryReceiptToReceiverRepository extends JpaRepository<DeliveryReceiptToReceiver,Long> {
    @Query("Select d from DeliveryReceiptToReceiver d where d.transactionPointSender.id =: tranId")
    public List<DeliveryReceiptToReceiver> getAllDeliveryReceiptToReceiverByTranId(@Param("tranId") Long tranId);

    @Query("SELECT d FROM DeliveryReceiptToReceiver d WHERE d.transactionPointSender.id = :tranId AND d.status = 'TRANSFERED'")
    public List<DeliveryReceiptToReceiver> getAllCompletedDeliveryReceiptToReceiverByTranId(@Param("tranId") Long tranId);

    @Query("SELECT d FROM DeliveryReceiptToReceiver d WHERE d.aPackage.id = :packageId")
    public DeliveryReceiptToReceiver getDeliveryReceiptToReceiverByPackageId(@Param("packageId") Long packageId);


}
