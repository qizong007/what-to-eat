package com.fzufood.service;

import com.fzufood.dto.*;

public interface DishService {

    JsonObject<UpdateDishTag> updateDishTag(Integer userId, Integer dishId, Integer tagId);
    Integer updateDishStar(Integer userId, Integer dishId, Double star);
    JsonObject<DishInfo> getDishInfo(Integer dishId);
    JsonObject<Favorites> favorites(Integer userId);

}
