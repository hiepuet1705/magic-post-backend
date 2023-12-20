package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;

import java.util.List;

public interface TranManagerService {
    public TransactionPoint getTransactionPointByManagerId(Long managerId);
    public List<Package> getSentPackageInATransactionPoint(Long TranPointId);

    public List<Package> getCurrentPackagesInATransactionPoint(Long TranPointId);

    public List<Package> getReceivePackagesInATransactionPoint(Long tranPointId);

}
