package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCT;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptTC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryReceiptCTRepository extends JpaRepository<DeliveryReceiptCT,Long> {
    @Query("SELECT d FROM DeliveryReceiptCT d WHERE d.collectionPointSender.id = :colPointId")
    public List<DeliveryReceiptCT> getDeliveryRReceiptCTByCollectionPointId(@Param("colPointId") Long colPointId);

    @Query("SELECT d FROM DeliveryReceiptCT d WHERE d.collectionPointSender.id = :colPointId AND d.status = 'TRANSFERED'")
    public List<DeliveryReceiptCT> getSentDeliveryReceiptCTByCollectionPointId(@Param("colPointId") Long colPointId);

    @Query("SELECT d FROM DeliveryReceiptCT d WHERE d.collectionPointSender.id = :colPointId AND (d.status = 'TRANSFERED' OR d.status = 'CURR_HERE')")
    public List<DeliveryReceiptCT> getReceivedDeliveryReceiptCTByCollectionPointId(@Param("colPointId") Long colPointId);

    @Query("SELECT d FROM DeliveryReceiptCT d WHERE d.transactionPointReceiver.id = :tranId AND (d.status = 'TRANSFERED' OR d.status = 'CURR_HERE')")
    public List<DeliveryReceiptCT> getReceivedDeliveryReceiptCTByTransactionPointId(@Param("tranId") Long tranId);

    @Query("Select d from DeliveryReceiptCT d where d.aPackage.id =:packageId")
    public DeliveryReceiptCT getDeliveryReceiptCTByPackageId(@Param("packageId") Long packageId);
}
