package com.fzufood.service;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.JsonObject;
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

    @Test
    void info(){
        JsonObject<WindowEntry> jsonObject = windowService.info(1,1);
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }

}
