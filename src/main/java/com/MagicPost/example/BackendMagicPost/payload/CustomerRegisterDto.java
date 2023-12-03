package com.MagicPost.example.BackendMagicPost.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRegisterDto {

    private String firstName;
    private String lastName;
    private String address;
    private Long phoneNumber;

    private String username;
    private String password;

}
