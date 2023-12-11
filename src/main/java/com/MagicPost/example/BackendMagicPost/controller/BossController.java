package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.CollectionPoint;
import com.MagicPost.example.BackendMagicPost.service.BossService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/boss")
public class BossController {
    private BossService bossService;

    public BossController(BossService bossService) {
        this.bossService = bossService;
    }
    @GetMapping("/col-points")
    @PreAuthorize("hasRole('BOSS')")
    public List<CollectionPoint> getAllCollectionPoints(){

        return bossService.getAllCollectionPoint();
    }
}
