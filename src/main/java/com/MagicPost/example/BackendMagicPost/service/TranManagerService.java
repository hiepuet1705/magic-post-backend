package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;

import java.util.List;

public interface TranManagerService {
    public TransactionPoint getTransactionPointByManagerId(Long managerId);

    Long getTranPointIdOfCurrentStaff();
    public List<Package> getSentPackageInATransactionPoint();

    public List<Package> getCurrentPackagesInATransactionPoint();



    public List<Package> getReceivePackagesInATransactionPoint();

}
