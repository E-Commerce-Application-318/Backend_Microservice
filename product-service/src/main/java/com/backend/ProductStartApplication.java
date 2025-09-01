package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductStartApplication {
    public static void main(String[] args) {
        System.out.println("Hello product");
        SpringApplication.run(ProductStartApplication.class, args);
    }
}