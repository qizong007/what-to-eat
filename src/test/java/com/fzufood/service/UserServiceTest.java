package com.fzufood.service;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.JsonObject;
import com.fzufood.dto.UserInfo;
import com.fzufood.util.StatusCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author qizong007
 * @create 2020/11/18 17:23
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void login(){

    }

    // 测试通过
    @Test
    void getInfo(){
        JsonObject<UserInfo> jsonObject = userService.getInfo(9);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    @Test
    void updateInfo(){

    }

    @Test
    void search(){

    }

    // 测试通过
    @Test
    void feedback(){
        Integer code = userService.feedback(1,"这小程序不得行啊~");
        Assertions.assertEquals(StatusCode.SUCCESS,code);
    }

}
