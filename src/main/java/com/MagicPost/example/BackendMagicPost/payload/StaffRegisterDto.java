package com.MagicPost.example.BackendMagicPost.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class StaffRegisterDto {
    private String lastName;
    private String firstName;

    private String username;
    private String password;

    private String phoneNumber;
    private String address;
}
