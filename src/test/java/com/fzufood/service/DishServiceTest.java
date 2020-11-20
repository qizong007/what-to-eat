package com.fzufood.service;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.*;
import com.fzufood.http.Code;
import com.fzufood.util.StatusCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author qizong007
 * @create 2020/11/17 18:58
 */
@SpringBootTest
public class DishServiceTest {

    @Autowired
    private DishService dishService;

    // 测试通过
    @Test
    void updateDishTag(){
        JsonObject<UpdateDishTag> jsonObject = dishService.updateDishTag(1,1,null);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void updateDishStar(){
        Code code = dishService.updateDishStar(1,1,3.0);
        Assertions.assertEquals(StatusCode.SUCCESS,code);
    }

    // 测试通过
    @Test
    void getDishInfo(){
        JsonObject<DishInfo> jsonObject = dishService.getDishInfo(2,10);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void favorites(){
        JsonObject<Favorites> jsonObject = dishService.favorites(10);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }


}
