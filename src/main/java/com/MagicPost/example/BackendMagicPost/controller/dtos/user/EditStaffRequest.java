package com.MagicPost.example.BackendMagicPost.controller.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditStaffRequest {
    private String lastName;
    private String firstName;
    private String address;
    private String phoneNumber;
    private String username;
    private String password;
    private String type;
}
