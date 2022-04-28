package com.example.demo.sequriti;
import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}