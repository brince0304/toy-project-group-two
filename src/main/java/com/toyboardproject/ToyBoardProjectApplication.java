package com.toyboardproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan

public class ToyBoardProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToyBoardProjectApplication.class, args);
    }

}
