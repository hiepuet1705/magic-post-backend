package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCC;
import com.MagicPost.example.BackendMagicPost.entity.DeliveryReceiptCT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryReceiptCCRepository extends JpaRepository<DeliveryReceiptCC,Long> {
    @Query("SELECT d FROM DeliveryReceiptCC d WHERE d.collectionPointSender.id = :colPointId")
    public List<DeliveryReceiptCC> getDeliveryRReceiptCCByCollectionPointId(@Param("colPointId") Long colPointId);

    @Query("SELECT d FROM DeliveryReceiptCC d WHERE d.collectionPointSender.id = :colPointId AND d.status = 'TRANSFERED'")
    public List<DeliveryReceiptCC> getSentDeliveryReceiptCCByCollectionPointId(@Param("colPointId") Long colPointId);

    @Query("SELECT d FROM DeliveryReceiptCC d WHERE d.collectionPointSender.id = :colPointId AND (d.status = 'TRANSFERED' OR d.status = 'CURR_HERE')")
    public List<DeliveryReceiptCC> getReceivedDeliveryReceiptCCByCollectionPointId(@Param("colPointId") Long colPointId);
}
