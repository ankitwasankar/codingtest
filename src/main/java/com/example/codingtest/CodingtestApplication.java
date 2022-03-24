package com.example.codingtest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Simplified FX Trading Platform API", version = "1.0", description = "Simplified FX Trading Platform"))
public class CodingtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodingtestApplication.class, args);
    }

}
