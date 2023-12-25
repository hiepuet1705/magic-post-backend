package com.MagicPost.example.BackendMagicPost.service;

import com.MagicPost.example.BackendMagicPost.entity.User;
import com.MagicPost.example.BackendMagicPost.repository.UserRepository;
import com.MagicPost.example.BackendMagicPost.utils.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private SecurityContextUtil securityContextUtil;

    public UserServiceImpl(UserRepository userRepository, SecurityContextUtil securityContextUtil) {
        this.userRepository = userRepository;
        this.securityContextUtil = securityContextUtil;
    }

    @Override
    public User getCurrentUser() {
        Long currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            return userRepository.getReferenceById(currentUserId);
        }
        throw new RuntimeException("User ID is null");
    }

    @Override
    public Long getCurrentUserId() {
        return securityContextUtil.getCurrentUserId();
    }
}
