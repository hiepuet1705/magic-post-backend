package com.MagicPost.example.BackendMagicPost.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StaffRegisterDto {
    private String name;
    private String username;
    private String password;
    private String phoneNumber;
    private Long pointId;


}
