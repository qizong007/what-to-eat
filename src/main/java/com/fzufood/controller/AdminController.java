package com.fzufood.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author qizong007
 * @create 2020/11/22 21:28
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

}
