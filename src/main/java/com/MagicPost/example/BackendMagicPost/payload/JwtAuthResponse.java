package com.MagicPost.example.BackendMagicPost.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

}
