package com.fzufood.repository;
import com.fzufood.entity.Window;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
@Mapper
public interface WindowMapper {

    List<Window> findAll();
    Window findById(Integer id);
    void save(Window window);
}