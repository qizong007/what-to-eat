package com.fzufood.service;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.JsonObject;
import com.fzufood.dto.Recommend;
import com.fzufood.dto.WindowEntry;
import com.fzufood.http.Code;
import com.fzufood.service.impl.WindowServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

/**
 * @Author qizong007
 * @create 2020/11/18 17:23
 */
@SpringBootTest
public class WindowServiceTest {

    @Autowired
    private WindowService windowService;

    @Test
    void recommend() throws FileNotFoundException {
        JsonObject<Recommend> jsonObject = windowService.recommend(2,10);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void info() throws FileNotFoundException {
        JsonObject<WindowEntry> jsonObject = windowService.info(1,1);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void getMarkedWindow() throws FileNotFoundException {
        JsonObject<Recommend> jsonObject = windowService.getMarkedWindow(1);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

    // 测试通过
    @Test
    void updateMarkedWindow() throws FileNotFoundException {
        Code code = windowService.updateMarkedWindow(1,1);
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

    @Test
    void pics() throws FileNotFoundException {
        windowService.getWindowPngSrc(3);
    }
}
