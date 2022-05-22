package com.example.demo.Configurations;

import com.example.demo.Entiti.Order;
import com.example.demo.Services.OrderServiceImp;
import com.example.demo.Services.PayServiseImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.io.IOException;

@Configuration
@EnableScheduling
public class Scheduledes {
    public final OrderServiceImp orderServiceImp;
    @Autowired
    public Scheduledes(PayServiseImp payServiseImp, OrderServiceImp orderServiceImp) {
        this.orderServiceImp = orderServiceImp;
    }
    @Transactional
    @Scheduled(cron = "1 * * * * ?")
    public  void deleteUnpaidPayment() throws IOException {
        //do something here

        orderServiceImp.delete();


    }
}
