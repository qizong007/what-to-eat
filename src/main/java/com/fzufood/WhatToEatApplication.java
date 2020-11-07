package com.fzufood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fzufood.*")
public class WhatToEatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatToEatApplication.class, args);
    }

}
