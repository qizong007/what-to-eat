package com.fzufood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author qizong007
 * @create 2020/11/27 13:22
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/404")
    public String error_404() {
        return "error/404";
    }

    @RequestMapping("/500")
    public String error_500() {
        return "error/500";
    }

}
