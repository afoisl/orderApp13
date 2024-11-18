package com.sparta.orderapp13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

//@EnableJpaAuditing
@EnableMethodSecurity
@SpringBootApplication
public class OrderApp13Application {

    public static void main(String[] args) {
        SpringApplication.run(OrderApp13Application.class, args);
    }

}
