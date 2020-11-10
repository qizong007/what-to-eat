package com.fzufood.service.impl;

import com.fzufood.dto.DishEntry;
import com.fzufood.dto.DishInfo;
import com.fzufood.dto.UpdateDishTag;
import com.fzufood.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Override
    public UpdateDishTag updateDishTag(Integer userId, Integer dishId, Integer tagId) {
        return null;
    }

    @Override
    public Integer updateDishStar(Integer userId, Integer dishId, Double star) {
        return null;
    }

    @Override
    public DishInfo getDishInfo(Integer dishId) {
        return null;
    }

    @Override
    public List<DishEntry> favorites(Integer userId) {
        return null;
    }
}
