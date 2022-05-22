package com.example.demo.Services;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.Entiti.Order;

import java.util.List;

public interface OrderService {
    void delete();
    void deleteById(int id);
    void save(OrderDTO order);
    OrderDTO findById(int id);
    List<OrderDTO> findByFrom(String from);
}
