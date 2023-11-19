package com.example.authservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean("usuarioClientRest")
    public RestTemplate registarRestTemplate() { return new RestTemplate(); }
}
