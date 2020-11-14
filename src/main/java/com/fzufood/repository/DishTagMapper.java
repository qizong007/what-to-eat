package com.fzufood.repository;

import com.fzufood.entity.DishTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author buzhouI
 */
@Repository
@Mapper
public interface DishTagMapper {

    /**
     * 查询所有dishTag(userId, dishId, tagId)
     * @return List<DishTag>
     */
    List<DishTag> listDishTags();

    /**
     * 根据dishId查询该dishId拥有的所有tagId
     * @param dishId
     * @return List<Integer>
     */
    List<Integer> listTagIdsByDishId(Integer dishId);

    /**
     * 插入新的dishTag(userId, dishId, tagId)
     * @param dishTag
     * @return int
     */
    int saveDishTag(DishTag dishTag);

    /**
     * 根据userId删除该userId所有的dishTag
     * @param userId
     * @return int
     */
    int removeDishTagsByUserId(Integer userId);

    /**
     * 根据dishId删除该dishId所有的dishTag
     * @param dishId
     * @return int
     */
    int removeDishTagsByDishId(Integer dishId);

    /**
     * 根据tagId删除该tagId所有的dishTag
     * @param tagId
     * @return int
     */
    int removeDishTagsByTagId(Integer tagId);
}
