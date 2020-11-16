package com.fzufood;

import com.fzufood.dto.DishEntry;
import com.fzufood.dto.DishInfo;
import com.fzufood.dto.SystemInfo;
import com.fzufood.dto.UserInfo;
import com.fzufood.entity.Canteen;
import com.fzufood.entity.Tag;
import com.fzufood.entity.User;
import com.fzufood.entity.Window;
import com.fzufood.repository.CanteenMapper;
import com.fzufood.repository.TagMapper;
import com.fzufood.repository.UserMapper;
import com.fzufood.repository.WindowMapper;
import com.fzufood.service.DishService;
import com.fzufood.service.SystemService;
import com.fzufood.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    private TagMapper tagMapper;
    @Autowired
    private WindowMapper windowMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void findAll(){
        List<Canteen> canteens = canteenMapper.listCanteens();
        for(Canteen canteen : canteens){
            System.out.println(canteen.getCanteenName());
        }
//        List<Tag> tags = tagMapper.findAll();
//        for(Tag tag : tags){
//            System.out.println(tag.getContent());
//        }
//        List<Window> windows = windowMapper.findAll();
//        for(Window window : windows){
//            System.out.println(window.getWindowName());
//        }
    }
//
//    @Test
//    public void canteenFindById(){
//        Canteen canteen = canteenMapper.findById(1);
//        System.out.println(canteen.getCanteenName());
//    }
//
//    @Test
//    public void tagFindById(){
//        Tag tag = tagMapper.findById(1);
//        System.out.println(tag.getContent());
//    }
//
//    @Test
//    public void save(){
//        Canteen canteen = new Canteen();
//        canteen.setCanteenName("朝阳餐厅");
//        canteenMapper.save(canteen);
//        System.out.println(canteen.getCanteenName());
//        Window window = new Window();
//        window.setWindowName("麻辣香锅");
//        windowMapper.save(window);
//        System.out.println(window.getWindowName());
//    }
//    @Test
//    public void windowFindById(){
//        Window window = windowMapper.findById(1);
//        System.out.println(window.getWindowName());
//    }
    @Autowired
    private SystemService systemService;

    @Test
    public void testForGetSystemInfo(){
        SystemInfo systemInfo = systemService.getInfo();
        List<Canteen> canteens = systemInfo.getCanteens();
        for(Canteen c : canteens){
            System.out.println(c.getCanteenName());
        }
    }

    @Autowired
    private DishService dishService;

    @Test
    public void testForGetDishInfo(){
        DishInfo dishInfo = dishService.getDishInfo(3);
        System.out.println(dishInfo.toString());
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testForUser(){
        List<User> userList = userMapper.listUsers();
        for(User user : userList){
            System.out.println(user.getUserName());
        }

    }


}
