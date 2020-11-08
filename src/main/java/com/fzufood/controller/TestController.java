package com.fzufood.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @ApiOperation("测试接口")
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

}

