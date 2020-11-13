package com.fzufood.repository;

import com.fzufood.entity.Canteen;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class CanteenMapperTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private CanteenMapper canteenMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    void findById() {
        Canteen canteen = canteenMapper.getCanteenById(1);
        System.out.println(canteen);
    }

    @Test
    void findAll() {
        List<Canteen> canteens = canteenMapper.listCanteens();
        for(Canteen canteen : canteens) {
            System.out.println(canteen);
        }
    }
}
