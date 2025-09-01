package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthStartApplication {
    public static void main(String[] args) {
        System.out.println("Hello auth");
        SpringApplication.run(AuthStartApplication.class, args);
    }
}