package com.MagicPost.example.BackendMagicPost.repository;

import com.MagicPost.example.BackendMagicPost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByPhoneNumber(String phoneNumber);
    @Query("Select u from User u where u.phoneNumber =:phoneNumber")
    User getUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
