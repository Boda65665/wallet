package com.example.demo.Repositories;

import com.example.demo.Entiti.Card;
import com.example.demo.Entiti.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Card findById(int id);
    Card findByNumber(String number);
    Card findByUser(Users users);
}
