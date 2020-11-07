package com.fzufood;

import com.fzufood.entity.Canteen;
import com.fzufood.entity.Tag;
import com.fzufood.repository.CanteenMapper;
import com.fzufood.repository.TagMapper;
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

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void findAll(){
        List<Canteen> canteens = canteenMapper.findAll();
        for(Canteen canteen : canteens){
            System.out.println(canteen.getCanteenName());
        }
//        List<Tag> tags = tagMapper.findAll();
//        for(Tag tag : tags){
//            System.out.println(tag.getContent());
//        }
    }

    @Test
    public void canteenFindById(){
        Canteen canteen = canteenMapper.findById(1);
        if(canteen != null)
            System.out.println(canteen.getCanteenName());
    }

    @Test
    public void tagFindById(){
        Tag tag = tagMapper.findById(1);
        System.out.println(tag.getContent());
    }

    @Test
    public void save(){
        Canteen canteen = new Canteen();
        canteen.setCanteenName("朝阳餐厅");
        canteenMapper.save(canteen);
        System.out.println(canteen.getCanteenName());
    }

}
