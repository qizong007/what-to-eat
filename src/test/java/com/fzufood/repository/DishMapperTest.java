package com.fzufood.repository;

import com.fzufood.entity.Canteen;
import com.fzufood.entity.Dish;
import com.fzufood.entity.Tag;
import com.fzufood.entity.Window;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
@SpringBootTest
public class DishMapperTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private DishMapper dishMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    void findDishById() {
        Dish dish = dishMapper.getDishById(1);
        System.out.println(dish);
    }
    @Test
    void findTagById(){
        List<Tag> tags= dishMapper.listTagsById(3);
        for(Tag tag : tags) {
            System.out.println(tag);
        }
    }
    @Test
    void findDishesAll() {
        List<Dish> dishes = dishMapper.listDishes();
        for(Dish dish : dishes) {
            System.out.println(dish);
        }
    }

    @Test
    void listDishesByNameTest() {
        List<Dish> dishes = dishMapper.listDishesByName("薯");
        for(Dish dish : dishes) {
            System.out.println(dish);
        }
    }

    @Test
    void addDish() {
        Window window =new Window();
        window.setWindowId(1);
        Dish dish = new Dish();
        dish.setDishName("添加了一道新菜");
        dish.setByWeight(false);
        dish.setDishId(5000);
        dish.setWindow(window);
        System.out.println(dishMapper.saveDish(dish));
        findDishesAll();
    }
    @Test
    void removeDishById(){
        System.out.println(dishMapper.removeDishById(5000));
        findDishesAll();
    }
    @Test
    void updateDishById(){
        Window window =new Window();
        window.setWindowId(1);
        Dish dish = new Dish();
        dish.setDishName("更改菜品信息");
        dish.setDishId(5000);
        dish.setWindow(window);
        System.out.println(dishMapper.updateDish(dish));
        findDishesAll();
    }
}

