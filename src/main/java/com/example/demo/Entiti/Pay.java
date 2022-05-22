package com.example.demo.Entiti;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "pays")
public class Pay {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "comment")
    private  String comment;
    @Column(name = "summa")
    int summa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "from_wallet")
    Wallet from;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "to_wallet")
    Wallet to;

}
