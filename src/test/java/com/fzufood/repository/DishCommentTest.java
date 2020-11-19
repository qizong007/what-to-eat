package com.fzufood.repository;

import com.fzufood.entity.DishComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class DishCommentTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private DishCommentMapper dishCommentMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    void listDishCommentsTest() {
        List<DishComment> dishComments = dishCommentMapper.listDishComments();
        for(DishComment dishComment: dishComments) {
            System.out.println(dishComment);
        }
    }

    @Test
    void listDishCommentsByUserIdTest() {
        List<DishComment> dishComments = dishCommentMapper.listDishCommentsByUserId(1);
        for(DishComment dishComment: dishComments) {
            System.out.println(dishComment);
        }
    }

    @Test
    void listDishCommentsByDishIdTest() {
        List<DishComment> dishComments = dishCommentMapper.listDishCommentsByDishId(1);
        for(DishComment dishComment: dishComments) {
            System.out.println(dishComment);
        }
    }

    @Test
    void getDishCommentByUserIdDishIdTest() {
        DishComment dishComment = dishCommentMapper.getDishCommentByUserIdDishId(1,1);
        System.out.println(dishComment);
    }

    @Test
    void getCountUserByDishIdTest() {
        System.out.println(dishCommentMapper.getCountUserByDishId(1));
    }

    @Test
    void getAvgStarsByDishIdTest() {
        System.out.println(dishCommentMapper.getAvgStarsByDishId(1));
    }

    @Test
    void getCountUserByWindowIdTest() {
        System.out.println(dishCommentMapper.getCountUserByWindowId(1));
    }

    @Test
    void getAvgStarsByWindowIdTest() {
        System.out.println(dishCommentMapper.getAvgStarsByWindowId(1));
    }

    @Test
    void saveDishCommentTest() {
        DishComment dishComment = new DishComment();
        dishComment.setUserId(1);
        dishComment.setDishId(99);
        dishComment.setStars(5.0);
        dishCommentMapper.saveDishComment(dishComment);
    }

    @Test
    void updateDishCommentTest() {
        DishComment dishComment = new DishComment();
        dishComment.setUserId(1);
        dishComment.setDishId(99);
        dishComment.setStars(4.9);
        dishCommentMapper.updateDishComment(dishComment);
    }

    @Test
    void removeDishCommentTest() {
        dishCommentMapper.removeDishComment(1,99);
    }
}
