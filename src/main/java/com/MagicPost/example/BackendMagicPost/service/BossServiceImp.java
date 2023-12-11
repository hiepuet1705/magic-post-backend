package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.CollectionPoint;
import com.MagicPost.example.BackendMagicPost.repository.CollectionPointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BossServiceImp implements BossService{
    private CollectionPointRepository collectionPointRepository;

    public BossServiceImp(CollectionPointRepository collectionPointRepository) {
        this.collectionPointRepository = collectionPointRepository;
    }

    @Override
    public List<CollectionPoint> getAllCollectionPoint() {
        List<CollectionPoint> collectionPoints =  collectionPointRepository.findAll();
        return collectionPoints;
    }
}
