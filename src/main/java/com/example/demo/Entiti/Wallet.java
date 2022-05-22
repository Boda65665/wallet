package com.example.demo.Entiti;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "wallet")
@Data
public class Wallet {
    @Column(name = "wallet_adres")
    String adres;
    @Column(name = "balance")
    int balance;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @OneToOne()
    @JoinColumn(name = "user_id")
    private Users user;

}
