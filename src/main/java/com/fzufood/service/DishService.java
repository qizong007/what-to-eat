package com.fzufood.service;

import com.fzufood.dto.*;
import com.fzufood.http.Code;

public interface DishService {

    JsonObject<UpdateDishTag> updateDishTag(Integer userId, Integer dishId, Integer tagId);
    Code updateDishStar(Integer userId, Integer dishId, Double star);
    JsonObject<DishInfo> getDishInfo(Integer dishId,Integer userId);
    JsonObject<Favorites> favorites(Integer userId);

}
