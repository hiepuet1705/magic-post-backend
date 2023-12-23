package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.Package;

import java.util.List;

public interface ColManagerService {
     List<Package> getSentPackageInACollectionPoint(Long colPointId);

     List<Package> getCurrPackageInACollectionPoint(Long colPointId);

     List<Package> getReceivePackageInACollectionPoint(Long colPointId);
}
