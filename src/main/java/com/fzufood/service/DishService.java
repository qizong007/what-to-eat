package com.fzufood.service;

import com.fzufood.dto.DishEntry;
import com.fzufood.dto.DishInfo;
import com.fzufood.dto.UpdateDishTag;

import java.util.List;

public interface DishService {

    UpdateDishTag updateDishTag(Integer userId, Integer dishId, Integer tagId);
    Integer updateDishStar(Integer userId,Integer dishId,Double star);
    DishInfo getDishInfo(Integer dishId);
    List<DishEntry> favorites(Integer userId);

}
