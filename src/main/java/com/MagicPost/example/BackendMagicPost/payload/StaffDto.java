package com.MagicPost.example.BackendMagicPost.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaffDto {
    private Long id;
    private Boolean isManager = false;

    private String type = "";

    private UserDto userDto;

    private PointDto pointDto;

}
