package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.*;
import com.MagicPost.example.BackendMagicPost.entity.Package;

import java.util.List;

public interface BossService {
    public List<CollectionPoint> getAllCollectionPoints();

    public List<TransactionPoint> getAllTransactionPoints();

    public StaffTransaction getManagerOfATranPoint(Long tranId);

    public StaffCollection getManagerOfAColPoint(Long tranId);

    public List<Package> getPackagesInATransactionPoint(Long tranId);

    public  List<Package> getPackagesInACollectionPoint(Long colId);

    public List<StaffTransaction> getStaffFromATransactionPoint(Long tranId);
    public List<StaffCollection> getStaffFromACollectionPoint(Long colId);

}
