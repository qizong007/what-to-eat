package com.fzufood.controller;

import com.fzufood.entity.Dish;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @ApiOperation("给方法加注释")
    @GetMapping("/hello2")
    public String hello2(@ApiParam("用户名") String name){
        return "Hello" + name;
    }

    @GetMapping("/dish")
    public Dish dish(String name, BigDecimal price){
        return new Dish(1,name,price,false,null);
    }

}

