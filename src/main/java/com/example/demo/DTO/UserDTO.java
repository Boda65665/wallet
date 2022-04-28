package com.example.demo.DTO;

import com.example.demo.Entiti.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDTO{
    int id;
    Role role;
    @Size(min = 5,max = 15,message = "password for 5 to 15 symbols")
    String password;
    @Size(min = 1,message = "min 1 symbol")
    @Email(message = "No valid email")
    String email;
    @Size(min = 3,max = 15,message = "username for 5 to 15 symbols")
    String username;

    public UserDTO(String password, String email, String username) {
        this.password = password;
        this.email = email;
        this.username = username;
    }
    public UserDTO(){}
}
