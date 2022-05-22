package com.example.demo;

import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

public class Scheduledes {
    @Scheduled(cron = "5 * * * * ?")
    public final void deleteUnpaidPayment() throws IOException {
        //do something here
    }
}
