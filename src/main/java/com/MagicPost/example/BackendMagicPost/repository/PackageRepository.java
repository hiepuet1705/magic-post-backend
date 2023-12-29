package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package,Long> {

    @Query("SELECT p FROM Package p JOIN FETCH p.sender WHERE p.transactionPoint = :tranId")
     List<Package> getPackagesInTransactionPoint(@Param("tranId") Long tranId);


    @Query("SELECT p FROM Package p JOIN FETCH p.sender WHERE p.collectionPoint = :colId")
     List<Package> getPackagesInCollectionPoint(@Param("colId") Long colId);

    @Query("SELECT p FROM Package p WHERE p.transactionPoint = :tranId AND p.status = 'AT_TRANSACTION_POINT'")
     List<Package> getCurrentPackageInATransactionPoint(@Param("tranId") Long tranId);

    @Query("SELECT p FROM Package p WHERE p.collectionPoint = :colId AND p.status = 'AT_COLLECTION_POINT'")
     List<Package> getCurrentPackagesInCollectionPoint(@Param("colId") Long colId);

    @Query("SELECT p FROM Package p WHERE p.sender.id = :customerId")
    List<Package> getPackagesByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT p FROM Package p WHERE p.transactionPoint = :tranId and p.status = 'TRANSFERING'")
    List<Package> getPendingPackageInATransactionPoint(@Param("tranId") Long tranId);

    @Query("SELECT p FROM Package p WHERE p.collectionPoint = :colId and p.status = 'TRANSFERING'")
    List<Package> getPendingPackageInACollectionPoint(@Param("colId") Long colId);


    @Query("SELECT p FROM Package p WHERE p.hashKey = :hashKey")
    Package getPackageByHashId(@Param("hashKey") String hashId);






}
