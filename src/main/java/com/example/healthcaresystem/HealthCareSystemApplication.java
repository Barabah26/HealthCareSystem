package com.example.healthcaresystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthCareSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthCareSystemApplication.class, args);
        System.out.println("http://localhost:8080/swagger-ui/index.html");
    }

}
