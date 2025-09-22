package com.backend;

import com.backend.ddd.application.service.OrderAppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderStartApplication {
    public static void main(String[] args) {
        System.out.println("Hello order");
        SpringApplication.run(OrderStartApplication.class, args);
    }
}