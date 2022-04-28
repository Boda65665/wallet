package com.example.demo.Services;

import com.example.demo.DTO.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService{
    void saveUser(UserDTO usersDto);
    UserDTO findByEmail(String email);
    void deleteUser(int id);

    List<UserDTO> findAll();

    UserDTO findById(int id);


}