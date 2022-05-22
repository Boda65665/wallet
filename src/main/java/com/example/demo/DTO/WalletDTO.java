package com.example.demo.DTO;

import lombok.Data;

@Data
public class WalletDTO {
    String adres;
    int balance;
    int id;
    UserDTO user;
}
