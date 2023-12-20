package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;

import java.util.List;

public interface ColManagerService {
    public List<Package> getSentPackageInACollectionPoint(Long colPointId);

    public List<Package> getCurrPackageInACollectionPoint(Long colPointId);

    public List<Package> getReceivePackageInACollectionPoint(Long colPointId);
}
