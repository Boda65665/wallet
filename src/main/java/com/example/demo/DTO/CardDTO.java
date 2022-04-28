package com.example.demo.DTO;

import lombok.Data;

@Data
public class CardDTO {
     private int id;
     private int balance;
     private UserDTO userDTO;
     private String number;
     private String payment_system;
}
