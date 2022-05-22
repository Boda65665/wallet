package com.example.demo.Entiti;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "code")
    int code;
    @Column(name = "time")
    Date date;
    @Column(name = "comment")
    String comment;
    @Column(name = "summa")
    int summa;
    @Column(name = "from_adres")
    String from;
    @Column(name = "to_adres")
    String to;
}
