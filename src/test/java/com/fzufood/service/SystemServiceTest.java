package com.fzufood.service;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.JsonObject;
import com.fzufood.dto.SystemInfo;
import com.fzufood.entity.Canteen;
import com.fzufood.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author qizong007
 * @create 2020/11/18 17:23
 */
@SpringBootTest
public class SystemServiceTest {

    @Autowired
    private SystemService systemService;

    // 测试通过
    @Test
    void getInfo(){
        JsonObject<SystemInfo> jsonObject = systemService.getInfo();
        String str = JSON.toJSONString(jsonObject);
        System.out.println(str);
    }
}
