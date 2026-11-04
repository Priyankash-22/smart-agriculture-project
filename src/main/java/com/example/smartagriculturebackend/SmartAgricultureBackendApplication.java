package com.example.smartagriculturebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartAgricultureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAgricultureBackendApplication.class, args);
    }

}
