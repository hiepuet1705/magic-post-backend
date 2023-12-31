package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.PackageDto;
import com.MagicPost.example.BackendMagicPost.payload.PointDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;

import java.util.List;

public interface BossService {
    public List<PointDto> getAllCollectionPoints();

    public List<PointDto> getAllTransactionPoints();

    public List<PackageDto> getAllPackages();

    public List<StaffDto> getAllStaff();

    public StaffTransaction getManagerOfATranPoint(Long tranId);

    public StaffCollection getManagerOfAColPoint(Long tranId);

    public List<Package> getPackagesInATransactionPoint(Long tranId);

    public  List<Package> getPackagesInACollectionPoint(Long colId);

    public List<StaffTransaction> getStaffFromATransactionPoint(Long tranId);
    public List<StaffCollection> getStaffFromACollectionPoint(Long colId);
}
