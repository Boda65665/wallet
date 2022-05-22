package com.example.demo.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    int code;
    Date date;

    int id;
    String comment;
    int summa;
    String from_adres;
    String to_adres;
}
