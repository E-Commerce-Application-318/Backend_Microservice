package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopStartApplication {
    public static void main(String[] args) {
        System.out.println("Hello Shop");
        SpringApplication.run(ShopStartApplication.class, args);
    }
}