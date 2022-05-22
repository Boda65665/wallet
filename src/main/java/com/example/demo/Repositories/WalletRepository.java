package com.example.demo.Repositories;

import com.example.demo.Entiti.Users;
import com.example.demo.Entiti.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByAdres(String adres);
    Wallet findById(int id);
    Wallet findByUser(Users users);
}
