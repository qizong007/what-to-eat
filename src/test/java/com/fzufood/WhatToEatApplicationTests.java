package com.fzufood;

import com.fzufood.entity.Canteen;
import com.fzufood.entity.Dish;
import com.fzufood.entity.Window;
import com.fzufood.repository.CanteenMapper;
import com.fzufood.repository.WindowMapper;
import com.fzufood.util.PicturePath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class WhatToEatApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    private CanteenMapper canteenMapper;
    @Autowired
    private WindowMapper windowMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

}
