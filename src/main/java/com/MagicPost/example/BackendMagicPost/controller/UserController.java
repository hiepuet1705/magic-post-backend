package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.entity.User;
import com.MagicPost.example.BackendMagicPost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(){
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }
}
