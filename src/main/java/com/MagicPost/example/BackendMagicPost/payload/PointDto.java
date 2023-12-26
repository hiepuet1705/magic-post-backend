package com.MagicPost.example.BackendMagicPost.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PointDto {
    private Long id;
    private String name;
    private String address;
}
