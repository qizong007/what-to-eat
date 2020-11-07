package com.fzufood.repository;

import com.fzufood.entity.Canteen;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CanteenMapper {

    List<Canteen> findAll();
    Canteen findById(Integer id);
    void save(Canteen canteen);

}
