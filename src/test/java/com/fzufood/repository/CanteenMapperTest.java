package com.fzufood.repository;

import com.fzufood.entity.Canteen;
import com.fzufood.entity.Window;
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
    void findCanteenById() {
        Canteen canteen = canteenMapper.getCanteenById(1);
        System.out.println(canteen);
    }
    @Test
    void findWindowById(){
        List<Window> windows= canteenMapper.listWindowsById(2);
        for(Window window : windows) {
            System.out.println(window);
        }
    }
    @Test
    void findCanteenAll() {
        List<Canteen> canteens = canteenMapper.listCanteens();
        for(Canteen canteen : canteens) {
            System.out.println(canteen);
        }
    }
    @Test
    void addCanteen() {
        Canteen canteen = new Canteen();
        canteen.setCanteenName("添加了一个新食堂");
        System.out.println(canteenMapper.saveCanteen(canteen));
        findCanteenAll();
    }
    @Test
    void updateCanteenById(){
        Canteen canteen = new Canteen();
        canteen.setCanteenName("修改食堂");
        canteen.setCanteenId(10);
        System.out.println(canteenMapper.updateCanteen(canteen));
        findCanteenAll();
    }
    @Test
    void removeCanteenById(){
        System.out.println(canteenMapper.removeCanteenById(10));
        findCanteenAll();
    }


}
