package com.fzufood.service;

import com.fzufood.dto.DishInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author qizong007
 * @create 2020/11/17 18:58
 */
@SpringBootTest
public class DishServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    void updateDishTag(){

    }

    @Test
    void updateDishStar(){

    }

    @Test
    void getDishInfo(){
        DishInfo dishInfo = dishService.getDishInfo(1);
        System.out.println(dishInfo.toString());
    }

    @Test
    void favorites(){

    }


}
