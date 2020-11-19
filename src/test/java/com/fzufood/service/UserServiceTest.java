package com.fzufood.service;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.JsonObject;
import com.fzufood.dto.UserInfo;
import com.fzufood.dto.UserLogin;
import com.fzufood.entity.Tag;
import com.fzufood.repository.TagMapper;
import com.fzufood.util.StatusCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        JsonObject<UserLogin> jsonObject = userService.login(s);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void getInfo(){
        JsonObject<UserInfo> jsonObject = userService.getInfo(9);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void updateInfo(){
        List<Tag> avoidTags = new ArrayList<>();
        List<Tag> preferTags = new ArrayList<>();
        avoidTags.add(tagMapper.getTagById(1));
        preferTags.add(tagMapper.getTagById(8));
        preferTags.add(tagMapper.getTagById(9));
        Integer code = userService.updateInfo(9,preferTags,avoidTags);
        Assertions.assertEquals(StatusCode.SUCCESS,code);
    }

    // FIXME
    @Test
    void search(){
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tagMapper.getTagById(1));
        tagList.add(tagMapper.getTagById(2));
        JsonObject<List<DishRecommend>> jsonObject = userService.search(null,tagList,null);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void feedback(){
        Integer code = userService.feedback(1,"这小程序不得行啊~");
        Assertions.assertEquals(StatusCode.SUCCESS,code);
    }

}
