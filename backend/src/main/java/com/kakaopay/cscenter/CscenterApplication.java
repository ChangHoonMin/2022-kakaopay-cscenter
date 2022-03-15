package com.kakaopay.cscenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CscenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CscenterApplication.class, args);
    }

}
