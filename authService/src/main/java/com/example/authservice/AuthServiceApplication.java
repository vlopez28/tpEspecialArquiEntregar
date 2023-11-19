package com.example.authservice;

import com.example.authservice.Config.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

    @Autowired
    SecurityConfiguration securityConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
