package com.fzufood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fzufood.*")
@ServletComponentScan
public class WhatToEatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatToEatApplication.class, args);
    }

}
