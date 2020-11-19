package com.fzufood.repository;

import com.fzufood.entity.Dish;
import com.fzufood.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author buzhouI
 */
@Repository
@Mapper
public interface DishMapper {

    /**
     * 查询所有dish(dishId, dishName, price, byWeight, window)
     * @return list<Dish>
     */
    List<Dish> listDishes();

    /**
     * 根据dishName模糊查询菜品
     * @param dishName
     * @return List<Dish>
     */
    List<Dish> listDishesByName(String dishName);

    /**
     * 根据dishId查询对应的dish(dishId, dishName, price, byWeight, window)
     * @param dishId
     * @return Dish
     */
    Dish getDishById(Integer dishId);

    /**
     * 根据dishId查询该dish拥有的所有tag
     * @param dishId
     * @return List<Tag>
     */
    List<Tag> listTagsById(Integer dishId);

    /**
     * 插入新的dish(dishId, dishName, price, byWeight, window)
     * @param dish
     * @return int
     */
    int saveDish(Dish dish);

    /**
     * 根据dishId更新dish(dishId, dishName, price, byWeight, window)
     * @param dish
     * @return int
     */
    int updateDish(Dish dish);

    /**
     * 根据dishId删除dish(dishId, dishName, price, byWeight, window)
     * @param dishId
     * @return int
     */
    int removeDishById(Integer dishId);
}
