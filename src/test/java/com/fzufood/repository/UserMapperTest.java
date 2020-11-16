package com.fzufood.repository;

import com.fzufood.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    void listUsersTest() {
        List<User> users = userMapper.listUsers();
        for(User user : users) {
            System.out.println(user);
        }
    }

    @Test
    void getUserByIdTest(){
        User user = userMapper.getUserById(1);
        System.out.println(user);
    }

    @Test
    void listLikeDishesByIdTest(){
        List<Dish> dishes = userMapper.listLikeDishesById(1);
        for(Dish dish: dishes) {
            System.out.println(dish);
        }
    }

    @Test
    void listMarkWindowsByIdTest() {
        List<Window> windows = userMapper.listMarkWindowsById(1);
        for(Window window: windows) {
            System.out.println(window);
        }
    }

    @Test
    void listMyFeedbackByIdTest() {
        List<Feedback> feedback = userMapper.listMyFeedbackById(1);
        for(Feedback f: feedback ) {
            System.out.println(f);
        }
    }

    @Test
    void listPreferTagsByIdTest() {
        List<Tag> tags = userMapper.listPreferTagsById(1);
        for(Tag tag: tags) {
            System.out.println(tag);
        }
    }

    @Test
    void listAvoidTagsByIdTest() {
        List<Tag> tags = userMapper.listAvoidTagsById(1);
        for(Tag tag: tags) {
            System.out.println(tag);
        }
    }

//    @Test
//    void saveUser() {
//        User user = new User();
//        user.setOpenId("123456");
//        user.setPhoneNumber("13202020202");
//        user.setUserName("saveTest");
//        user.setProfilePictureURI("path");
//        userMapper.saveUser(user);
//    }
}
