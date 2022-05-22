package com.example.demo.sequriti;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequestDTO {
    @NotNull(message = "поле не должно быть пустым")
    private String email;
    @NotNull(message = "поле не должно быть пустым")
    private String password;
}