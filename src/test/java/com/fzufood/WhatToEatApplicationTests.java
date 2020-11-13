package com.fzufood;

import com.fzufood.entity.Canteen;
import com.fzufood.entity.Tag;
import com.fzufood.entity.Window;
import com.fzufood.repository.CanteenMapper;
import com.fzufood.repository.TagMapper;
import com.fzufood.repository.WindowMapper;
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

//    @Test
//    public void findAll(){
////        List<Canteen> canteens = canteenMapper.findAll();
////        for(Canteen canteen : canteens){
////            System.out.println(canteen.getCanteenName());
////        }
////        List<Tag> tags = tagMapper.findAll();
////        for(Tag tag : tags){
////            System.out.println(tag.getContent());
////        }
//        List<Window> windows = windowMapper.findAll();
//        for(Window window : windows){
//            System.out.println(window.getWindowName());
//        }
//    }
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


}
