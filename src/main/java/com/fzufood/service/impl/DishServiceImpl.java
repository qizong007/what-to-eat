package com.fzufood.service.impl;

import com.fzufood.dto.DishEntry;
import com.fzufood.dto.DishInfo;
import com.fzufood.dto.UpdateDishTag;
import com.fzufood.entity.Dish;
import com.fzufood.entity.User;
import com.fzufood.repository.DishMapper;
import com.fzufood.repository.UserMapper;
import com.fzufood.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private UserMapper userMapper;

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
        Dish dish = dishMapper.getDishById(dishId);
        DishInfo dishInfo = new DishInfo();
        dishInfo.setDishName(dish.getDishName());
        dishInfo.setPrice(dish.getPrice());
        // TODO:dishInfo.setStar(dish);
        // TODO:dishInfo.setStarNum();
        dishInfo.setTagList(dish.getTags());
        return dishInfo;
    }

    @Override
    public List<DishEntry> favorites(Integer userId) {
        List<Dish> dishList = userMapper.listLikeDishesById(userId);
        List<DishEntry> dishEntries = new ArrayList<>();
        for(Dish dish : dishList){
            DishEntry dishEntry = new DishEntry();
            dishEntry.setDishId(dish.getDishId());
            dishEntry.setDishName(dish.getDishName());
            dishEntry.setPrice(dish.getPrice());
            // TODO:dishEntry.setStar(dish.get);
            dishEntries.add(dishEntry);
        }
        return dishEntries;
    }
}
