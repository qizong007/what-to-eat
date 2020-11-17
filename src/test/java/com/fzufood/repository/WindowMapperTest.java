package com.fzufood.repository;

import com.fzufood.entity.Dish;
import com.fzufood.entity.Window;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
@SpringBootTest
public class WindowMapperTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private WindowMapper windowMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }
    @Test
    void findWindowById() {
        Window window = windowMapper.getWindowById(32);
        System.out.println(window);
    }
    @Test
    void findDishesById(){
        List<Dish> dishes= windowMapper.listDishesById(2);
        for(Dish dish : dishes) {
            System.out.println(dish);
        }
    }
    @Test
    void findWindowAll() {
        List<Window> windows = windowMapper.listWindows();
        for(Window window : windows) {
            System.out.println(window);
        }
    }
//    @Test
//    void addWindow() {
//        Window window = new Window();
//        window.setWindowName("的规划复活甲");
//        System.out.println(windowMapper.saveWindow(window));
//        findWindowAll();
//    }
//    @Test
//    void removeWindowById(){
//        System.out.println(windowMapper.removeWindowById(10));
//    }
//    @Test
//    void updateWindowById(){
//        Window window= new Window();
//        window.setWindowName("222");
//        window.setWindowId(11);
//        System.out.println(windowMapper.updateWindow(window));
//
//    }
}