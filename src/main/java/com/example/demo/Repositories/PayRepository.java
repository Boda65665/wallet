package com.example.demo.Repositories;

import com.example.demo.DTO.PayDTO;
import com.example.demo.Entiti.Pay;
import com.example.demo.Entiti.Users;
import com.example.demo.Entiti.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayRepository extends JpaRepository<Pay, Integer>{
    List<Pay> findAllByTo(Wallet wallet);
    List<Pay> findAllByFrom(Wallet wallet);
    Pay findById(int id);


}
