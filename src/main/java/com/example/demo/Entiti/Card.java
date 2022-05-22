//package com.example.demo.Entiti;
//
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Table(name = "card")
//@Entity
//@Data
//public class Card {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "id")
//    private int id;
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//
//    public Integer getId() {
//        return id;
//    }
//    @Column(name = "balance")
//    private int balance;
//
//    @Column(name = "number")
//    private String number;
//
//    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "id_user")
//    private Users user;
//
//    @Column(name = "payment_system")
//    private String pay_sys;
//
//    @OneToMany(mappedBy = "card")
//    private List<Pay> payList;
//
//}
