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
     * 根据dishId查询该dish拥有的所有tagId
     * @param dishId
     * @return List<Integer>
     */
    List<Integer> listTagIdsByDishId(Integer dishId);

    /**
     * 根据dishId,tagId查询该dish某个tag拥有的用户列表
     * @param dishId,tagId
     * @return List<DishTag>
     */
    List<DishTag> listDishTagByDishIdAndTagId(Integer dishId,Integer tagId);

    /**
     * 根据tagId查询该tagId对应的dishTag
     * @param tagId
     * @return DishTag
     */
    DishTag getDishTagById(Integer tagId);

    /**
     * 根据tagId查询该tag拥有的所有DishTag
     * @param tagId
     * @return List<DishTag>
     */
    List<DishTag> listDishTagsByTagId(Integer tagId);

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
