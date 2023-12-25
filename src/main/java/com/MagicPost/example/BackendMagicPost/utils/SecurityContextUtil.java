package com.MagicPost.example.BackendMagicPost.utils;

import com.MagicPost.example.BackendMagicPost.entity.User;
import com.MagicPost.example.BackendMagicPost.exception.CustomApiException;
import com.MagicPost.example.BackendMagicPost.repository.UserRepository;
import com.MagicPost.example.BackendMagicPost.security.CustomAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityContextUtil {

    private UserRepository userRepository;

    public SecurityContextUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getCurrentUserId() {
        // user repo => get Id
        // Authention =>

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).
                orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Username not found"));


        CustomAuthentication principal = new CustomAuthentication(user.getId(),true, authentication.getAuthorities());

        return principal.getId();
    }
}