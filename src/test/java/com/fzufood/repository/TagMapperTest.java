package com.fzufood.repository;

import com.fzufood.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class TagMapperTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private TagMapper tagMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }
    @Test
    void findTagById() {
        Tag tag = tagMapper.getTagById(1);
        System.out.println(tag);
    }
    @Test
    void findTagAll() {
        List<Tag> tags = tagMapper.listTags();
        for(Tag tag : tags) {
            System.out.println(tag);
        }
    }
    @Test
    void addTag() {
        Tag tag = new Tag();
        tag.setTagId(11);
        tag.setContent("偏酸");;
        System.out.println(tagMapper.saveTag(tag));
        findTagAll();
    }
    @Test
    void updateTagById(){
        Tag tag = new Tag();
        tag.setContent("修改标签");
        tag.setTagId(10);
        System.out.println(tagMapper.updateTag(tag));
        findTagAll();
    }
    @Test
    void removeTagById(){
        System.out.println(tagMapper.removeTagById(11));
        findTagAll();
    }

}
