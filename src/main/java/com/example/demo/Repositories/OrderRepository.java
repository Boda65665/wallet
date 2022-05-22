package com.example.demo.Repositories;

import com.example.demo.Entiti.Order;
import com.example.demo.Entiti.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);
    List<Order> findAllByFrom(String from);
}
