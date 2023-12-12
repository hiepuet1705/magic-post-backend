package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.CollectionPoint;
import com.MagicPost.example.BackendMagicPost.entity.StaffTransaction;
import com.MagicPost.example.BackendMagicPost.entity.TransactionPoint;

import java.util.List;

public interface BossService {
    public List<CollectionPoint> getAllCollectionPoints();

    public List<TransactionPoint> getAllTransactionPoints();

    public StaffTransaction getManagerOfSTranPoint(Long tranId);
}
