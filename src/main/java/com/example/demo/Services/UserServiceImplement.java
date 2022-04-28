package com.example.demo.Services;

import com.example.demo.Converters.CardConverters;
import com.example.demo.Converters.UserConverters;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Entiti.Users;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService{
    @Autowired
    UserRepository repositori;

    UserConverters userConverters = new UserConverters();
    CardConverters cardConverters = new CardConverters();

    @Override
    public void saveUser(UserDTO usersDto) {
        Users users = new Users();
        users = userConverters.FromUserDTOinUsers(usersDto);
        repositori.save(users);
    }

    @Override
    public UserDTO findByEmail(String email) {
        Users users = repositori.findByEmail(email);
        UserDTO userDTO = userConverters.FromUsersInUserDTO(repositori.findByEmail(email));

        if(users.getCard()!=null) {
            userDTO.setCardDTO(cardConverters.CardInCardDTO(users.getCard()));
        }
        return userDTO;
    }



    @Override
    public void deleteUser(int id) {
        repositori.deleteById(id);
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();
        List<Users> user_list = new ArrayList<Users>();
        user_list = repositori.findAll();
        System.out.println(user_list.size());

        for(int i=0;i<user_list.size();i++){
            Users users = user_list.get(i);
            UserDTO userDTO = userConverters.FromUsersInUserDTO(user_list.get(i));

            if(users.getCard()!=null) {
                userDTO.setCardDTO(cardConverters.CardInCardDTO(users.getCard()));
            }

            userDTOList.add(i,userDTO);
            System.out.println(i);


        }
        return userDTOList;
    }

    @Override
    public UserDTO findById(int id){
    Users users = repositori.findById(id);
    UserDTO userDTO = userConverters.FromUsersInUserDTO(repositori.findById(id));

        if(users.getCard()!=null) {
        userDTO.setCardDTO(cardConverters.CardInCardDTO(users.getCard()));
    }
        return userDTO;
}
}
