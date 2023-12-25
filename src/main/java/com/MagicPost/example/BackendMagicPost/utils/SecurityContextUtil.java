package com.MagicPost.example.BackendMagicPost.utils;

import com.MagicPost.example.BackendMagicPost.entity.User;
import com.MagicPost.example.BackendMagicPost.repository.UserRepository;
import com.MagicPost.example.BackendMagicPost.security.CustomAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextUtil {

    public static Long getCurrentUserId() {
        CustomAuthentication principal = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        return principal.getId();
    }
}