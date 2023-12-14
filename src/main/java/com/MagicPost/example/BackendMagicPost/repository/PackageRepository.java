package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package,Long> {

    @Query("SELECT p FROM Package p JOIN FETCH p.sender WHERE p.transactionPoint = :tranId")
    public List<Package> getPackagesInTransactionPoint(@Param("tranId") Long tranId);


    @Query("SELECT p FROM Package p JOIN FETCH p.sender WHERE p.collectionPoint = :colId")
    public List<Package> getPackagesInCollectionPoint(@Param("colId") Long colId);

    public Package getPackageByDeReceiptTCId(Long deReceiptTCId);

}
