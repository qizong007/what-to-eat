package com.fzufood.service;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.*;
import com.fzufood.entity.Tag;
import com.fzufood.http.Code;
import com.fzufood.http.LoginResponse;
import com.fzufood.repository.TagMapper;
import com.fzufood.util.StatusCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author qizong007
 * @create 2020/11/18 17:23
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private TagMapper tagMapper;

    // 测试通过
    @Test
    void login(){
        String s = "063FrE0w3tvBkV2dWF1w39VYG04FrE0b";
        JsonObject<UserLogin> jsonObject = userService.login(new LoginResponse(s));
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void getInfo(){
        JsonObject<UserInfo> jsonObject = userService.getInfo(null);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void updateInfo(){
        List<Tag> avoidTags = new ArrayList<>();
        List<Tag> preferTags = new ArrayList<>();
        List<Tag> allList = new ArrayList<>();
        avoidTags.add(tagMapper.getTagById(1));
        preferTags.add(tagMapper.getTagById(8));
        preferTags.add(tagMapper.getTagById(9));
        allList.add(new Tag(10,"很淦"));
        Code code = userService.updateInfo(9,preferTags,avoidTags);
        Assertions.assertEquals(StatusCode.SUCCESS,code.getCode());
        getInfo();
    }

    // FIXME
    @Test
    void search() throws FileNotFoundException {
        JsonObject<Search> jsonObject = userService.search(null,null,3);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void feedback(){
        Code code = userService.feedback(1,"这小程序不得行啊~");
        Assertions.assertEquals(StatusCode.SUCCESS,code.getCode());
    }

}
