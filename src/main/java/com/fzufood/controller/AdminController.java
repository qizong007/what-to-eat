package com.fzufood.controller;

import com.fzufood.entity.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author qizong007
 * @create 2020/11/22 21:28
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/test")
    public String test(Model model){
        String str = "这只是个测试hhh";
        model.addAttribute("str",str);
        return "test";
    }

    /**
     * 登录请求，返回表单对象
     * @param model
     * @return String
     */
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("admin",new Admin());
        return "login";
    }

    /**
     * 接收表单，返回结果
     * @param admin
     * @return String
     */
    @PostMapping("/login")
    public String login(Admin admin){
        String adminName = admin.getAdminName();
        String password = admin.getAdminPassword();
        if(adminName.equals("qizong007") && password.equals("123456")){
            return "success";
        }else{
            return "login";
        }
    }


}
