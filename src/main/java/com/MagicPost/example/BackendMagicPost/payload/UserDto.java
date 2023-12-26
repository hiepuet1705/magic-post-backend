package com.MagicPost.example.BackendMagicPost.payload;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private Long id;
    private String lastName;
    private String firstName;

    private String address;
    private String phoneNumber;

    private String username;
    private String password;
}
