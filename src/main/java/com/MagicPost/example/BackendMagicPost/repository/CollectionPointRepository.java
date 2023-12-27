package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.CollectionPoint;
import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionPointRepository extends JpaRepository<CollectionPoint,Long> {
    @Query("select cp from CollectionPoint cp where cp.province =:province ")
    public CollectionPoint getCollectionPointByProvince(@Param("province") String province);

}
