package com.fzufood.service;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.JsonObject;
import com.fzufood.dto.Recommend;
import com.fzufood.dto.WindowEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @Author qizong007
 * @create 2020/11/18 17:23
 */
@SpringBootTest
public class WindowServiceTest {

    @Autowired
    private WindowService windowService;

    // FIXME
    @Test
    void recommend(){
        JsonObject<Recommend> jsonObject = windowService.recommend(1,1);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void info(){
        JsonObject<WindowEntry> jsonObject = windowService.info(1,1);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void getMarkedWindow(){
        JsonObject<Recommend> jsonObject = windowService.getMarkedWindow(1);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void updateMarkedWindow(){
        Integer code = windowService.updateMarkedWindow(1,1);
        System.out.println(code);
        JsonObject<Recommend> jsonObject = windowService.getMarkedWindow(1);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
        code = windowService.updateMarkedWindow(1,1);
        System.out.println(code);
        jsonObject = windowService.getMarkedWindow(1);
        str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

}
