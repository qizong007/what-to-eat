package com.fzufood.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
public class AdminMapperTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    void getAdminByNameAndPasswordTest() {
        System.out.println(adminMapper.getAdminByNameAndPassword("qizong007","123456"));
    }

    @Test
    void getAdminByIdAndPasswordTest() {
        System.out.println(adminMapper.getAdminByIdAndPassword(0,"123456"));
    }
}
