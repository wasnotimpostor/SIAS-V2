package com.example.demo.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private LocalDateTime loginTime = LocalDateTime.now();

    public JwtResponse(String token) {
        this.token = token;
    }
}
