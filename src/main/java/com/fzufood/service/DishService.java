package com.fzufood.service;

import com.fzufood.dto.*;

import java.util.List;

public interface DishService {

    JsonObject<UpdateDishTag> updateDishTag(Integer userId, Integer dishId, Integer tagId);
    Code updateDishStar(Integer userId, Integer dishId, Double star);
    JsonObject<DishInfo> getDishInfo(Integer dishId);
    JsonObject<List<DishEntry>> favorites(Integer userId);

}
