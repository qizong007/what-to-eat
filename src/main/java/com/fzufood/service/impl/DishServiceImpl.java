package com.fzufood.service.impl;

import com.fzufood.dto.DishEntry;
import com.fzufood.dto.DishInfo;
import com.fzufood.dto.UpdateDishTag;
import com.fzufood.entity.Dish;
import com.fzufood.entity.DishComment;
import com.fzufood.entity.User;
import com.fzufood.repository.DishCommentMapper;
import com.fzufood.repository.DishMapper;
import com.fzufood.repository.TagMapper;
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
    @Autowired
    private DishCommentMapper dishCommentMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public UpdateDishTag updateDishTag(Integer userId, Integer dishId, Integer tagId) {
        return null;
    }

    @Override
    public Integer updateDishStar(Integer userId, Integer dishId, Double star) {
        return null;
    }

    /**
     * 获取菜品信息接口
     * @author qizong007
     * @param dishId
     * @return
     */
    @Override
    public DishInfo getDishInfo(Integer dishId) {
        Dish dish = dishMapper.getDishById(dishId);
        DishInfo dishInfo = new DishInfo();
        dishInfo.setDishName(dish.getDishName());
        dishInfo.setPrice(dish.getPrice());
        dishInfo.setStar(countStarsOnDish(dishId));
        dishInfo.setStarNum(countStarsNumOnDish(dishId));
        dishInfo.setTagList(dish.getTags());
        return dishInfo;
    }

    /**
     * 最爱的菜接口
     * @author qizong007
     * @param userId
     * @return
     */
    @Override
    public List<DishEntry> favorites(Integer userId) {
        List<Dish> dishList = userMapper.listLikeDishesById(userId);
        List<DishEntry> dishEntries = new ArrayList<>();
        for(Dish dish : dishList){
            DishEntry dishEntry = new DishEntry();
            dishEntry.setDishId(dish.getDishId());
            dishEntry.setDishName(dish.getDishName());
            dishEntry.setPrice(dish.getPrice());
            dishEntry.setStar(countStarsOnDish(dish.getDishId()));
            dishEntries.add(dishEntry);
        }
        return dishEntries;
    }

    /**
     * 根据dishId，查出该道菜品星级
     * @author qizong007
     * @param dishId
     * @return
     */
    private Double countStarsOnDish(Integer dishId){
        List<DishComment> dishComments = dishCommentMapper.listDishCommentsByDishId(dishId);
        Double stars = 0.0;
        for(DishComment dishComment : dishComments){
            stars += dishComment.getStars();
        }
        return stars/dishComments.size();
    }

    /**
     * 根据dishId，查出该道菜品5种星级分别有几人
     * @author qizong007
     * @param dishId
     * @return
     */
    private Integer[] countStarsNumOnDish(Integer dishId){
        List<DishComment> dishComments = dishCommentMapper.listDishCommentsByDishId(dishId);
        Integer[] starNum = new Integer[5];
        Double star;
        for(DishComment dishComment : dishComments){
            star = dishComment.getStars();
            if(star.compareTo(1.0)==0){
                starNum[0]++;
            }
            else if(star.compareTo(2.0)==0){
                starNum[1]++;
            }
            else if(star.compareTo(3.0)==0){
                starNum[2]++;
            }
            else if(star.compareTo(4.0)==0){
                starNum[3]++;
            }
            else if(star.compareTo(5.0)==0){
                starNum[4]++;
            }
        }
        return starNum;
    }
}
