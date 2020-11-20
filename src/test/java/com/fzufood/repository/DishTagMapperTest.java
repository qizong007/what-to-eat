package com.fzufood.repository;

import com.fzufood.entity.DishTag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class DishTagMapperTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private DishTagMapper dishTagMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    void listDishTagsTest() {
        List<DishTag> dishTags = dishTagMapper.listDishTags();
        for(DishTag dishTag: dishTags){
            System.out.println(dishTag);
        }
    }

    @Test
    void listTagIdsByDishIdTest() {
//        List<Integer> tagIds = dishTagMapper.listTagIdsByDishId(1);
//        for(Integer tagId: tagIds) {
//            System.out.println(tagId);
//        }
    }

    @Test
    void listDishTagByDishIdAndTagIdTest() {
        List<DishTag> dishTags = dishTagMapper.listDishTagByDishIdAndTagId(1,1);
        for(DishTag dishTag: dishTags){
            System.out.println(dishTag);
        }
    }

    @Test
    void listDishTagsByTagIdTest() {
        List<DishTag> dishTags = dishTagMapper.listDishTagsByTagId(1);
        for(DishTag dishTag: dishTags){
            System.out.println(dishTag);
        }
    }

    @Test
    void countTagNumByWindowIdTest() {
        System.out.println(dishTagMapper.countTagNumByWindowId(1));
    }

    @Test
    void saveDishTagTest() {
        DishTag dishTag = new DishTag();
        for(int i = 0; i < 4; i++) {
            dishTag.setUserId(999991 + i);
            dishTag.setDishId(999991 + i);
            dishTag.setTagId(999991 + i);
            dishTagMapper.saveDishTag(dishTag);
        }
    }

    @Test
    void removeDishTagByDishTagTest() {
        DishTag dishTag = new DishTag();
        dishTag.setUserId(999991);
        dishTag.setDishId(999991);
        dishTag.setTagId(999991);
        dishTagMapper.removeDishTagByDishTag(dishTag);
    }

    @Test
    void removeDishTagsByUserIdTest() {
        dishTagMapper.removeDishTagsByUserId(999992);
    }

    @Test
    void removeDishTagsByDishIdTest() {
        dishTagMapper.removeDishTagsByDishId(999993);
    }

    @Test
    void removeDishTagsByTagIdTest() {
        dishTagMapper.removeDishTagsByTagId(999994);
    }
}
