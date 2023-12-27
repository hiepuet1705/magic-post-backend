package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.StaffCollection;
import com.MagicPost.example.BackendMagicPost.entity.StaffTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StaffCollectionRepository extends JpaRepository<StaffCollection,Long> {
    @Query("SELECT s FROM StaffCollection s WHERE s.isManager = true AND s.collectionPoint.id = :colId")
    public StaffCollection getStaffByIsManager(@Param("colId") Long colId);

    @Query("SELECT s FROM StaffCollection s WHERE s.user.id = :userId")
    public StaffCollection getStaffByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM StaffCollection s WHERE s.collectionPoint.id = :colId")
    public List<StaffCollection> getAllStaffByColId(@Param("colId") Long colId);
}
