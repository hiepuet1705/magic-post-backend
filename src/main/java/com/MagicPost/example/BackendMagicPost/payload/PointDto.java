package com.MagicPost.example.BackendMagicPost.payload;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

public class PointDto {
    private Long id = 0L;
    private String name = "";
    private String district="";
    private String province="";
}
