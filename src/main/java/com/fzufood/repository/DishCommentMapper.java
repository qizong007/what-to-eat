package com.fzufood.repository;

import com.fzufood.entity.DishComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author buzhouI
 */
@Repository
@Mapper
public interface DishCommentMapper {

    /**
     * 查询所有dishComment(commentId, stars, userId, dishId)
     * @return List<DishComment>
     */
    List<DishComment> listDishComments();

    /**
     * 根据userId查询该userId所有的dishComment(commentId, stars, userId, dishId)
     * @param userId
     * @return List<DishComment>
     */
    List<DishComment> listDishCommentsByUserId(Integer userId);

    /**
     * 根据dishId查询该dishId所有的dishComment(commentId, stars, userId, dishId)
     * @param dishId
     * @return List<DishComment>
     */
    List<DishComment> listDishCommentsByDishId(Integer dishId);

    /**
     * 根据(userId, dishId)查询对应的dishComment(commentId, stars, userId, dishId)
     * @param userId
     * @param dishId
     * @return DishComment
     */
    DishComment getDishCommentByUserIdDishId(@Param("userId") Integer userId, @Param("dishId") Integer dishId);

    /**
     * 插入新的dishComment(commentId, stars, userId, dishId)
     * @param dishComment
     * @return int
     */
    int saveDishComment(DishComment dishComment);

    /**
     * 根据(userId, dishId)更新dishComment(commentId, stars, userId, dishId) 只能改stars
     * @param dishComment
     * @return int
     */
    int updateDishComment(DishComment dishComment);

    /**
     * 根据(userId, dishId)删除dishComment(commentId, stars, userId, dishId)
     * @param userId
     * @param dishId
     * @return int
     */
    int removeDishComment(@Param("userId") Integer userId, @Param("dishId") Integer dishId);

    /**
     * 根据dishId查出给此dish评星级的总人数
     * @param dishId
     * @return Integer
     */
    Integer getCountUserByDishId(Integer dishId);

    /**
     * 根据dishId查出给此dish的评分
     * @param dishId
     * @return Double
     */
    Double getAvgStarsByDishId(Integer dishId);

    /**
     * 根据windowId查出给此window的所有菜评星级的总人数
     * @param windowId
     * @return Integer
     */
    Integer getCountUserByWindowId(Integer windowId);

    /**
     * 根据windowId查出给此window的评分
     * @param windowId
     * @return Integer
     */
    Double getAvgStarsByWindowId(Integer windowId);
}
