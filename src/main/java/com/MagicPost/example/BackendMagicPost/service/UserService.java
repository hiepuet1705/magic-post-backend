package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.User;

public interface UserService {
    User getCurrentUser();

    Long getCurrentUserId();
}
