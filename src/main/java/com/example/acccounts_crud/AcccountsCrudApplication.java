package com.example.acccounts_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class AcccountsCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcccountsCrudApplication.class, args);
    }

}
