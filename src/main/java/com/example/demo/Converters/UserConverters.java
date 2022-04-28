package com.example.demo.Converters;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entiti.Users;

public class UserConverters {
    public Users FromUserDTOinUsers(UserDTO user){
        Users users = new Users();
        users.setRole(user.getRole());
        users.setPassword(user.getPassword());
        users.setLogin(user.getUsername());
        users.setEmail(user.getEmail());
        users.setId(user.getId());
        return users;

    }
    public UserDTO FromUsersInUserDTO(Users user){
        UserDTO users = new UserDTO();
        users.setRole(user.getRole());
        users.setPassword(user.getPassword());
        users.setUsername(user.getLogin());
        users.setEmail(user.getEmail());
        users.setId(user.getId());
        return users;
    }
}
