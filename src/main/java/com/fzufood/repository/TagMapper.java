package com.fzufood.repository;

import com.fzufood.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TagMapper {

    List<Tag> findAll();
    Tag findById(Integer id);
    void save(String content);

}
