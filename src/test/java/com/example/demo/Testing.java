package com.example.demo;

import com.example.demo.Repositories.PayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class Testing {
    @Autowired
    PayRepository payRepository;
    @Test
    public void hello(){
        System.out.println(payRepository.findAll());
    }
}
