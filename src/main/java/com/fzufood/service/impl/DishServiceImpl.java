package com.fzufood.service.impl;

import com.fzufood.dto.DishEntry;
import com.fzufood.dto.DishInfo;
import com.fzufood.dto.UpdateDishTag;
import com.fzufood.entity.*;
import com.fzufood.repository.*;
import com.fzufood.service.DishService;
import com.fzufood.util.StatusCode;
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
    private DishTagMapper dishTagMapper;

    /**
     * 更新菜品标签
     * @author qizong007
     * @date 17:42 2020/11/14
     * @param userId,dishId,tagId
     * @return UpdateDishTag
     **/
    @Override
    public UpdateDishTag updateDishTag(Integer userId, Integer dishId, Integer tagId) {
        List<Integer> tagIdList = dishTagMapper.listTagIdsByDishId(dishId);
        DishTag dishTag = null;
        boolean hasTagged = false;
        for(Integer tagID : tagIdList){
            dishTag = dishTagMapper.getDishTagById(tagID);
            if(dishTag.getUserId() == userId){
                hasTagged = true;
                break;
            }
        }
        List<DishTag> userInThisDishTag = dishTagMapper.listDishTagByDishIdAndTagId(dishId,tagId);
        // 某道菜的某个标签多少人点过
        Integer count = userInThisDishTag.size();
        UpdateDishTag updateDishTag = new UpdateDishTag();
        updateDishTag.setDishName(dishMapper.getDishById(dishId).getDishName());
        updateDishTag.setTagId(tagId);
        updateDishTag.setCount(count);
        if(hasTagged){
            // 用户点过，现在就要取消 -- false
            dishTagMapper.removeDishTagsByDishId(dishId);
            updateDishTag.setMarkedTag(false);
        }else{
            // 用户还未点过，现在要点亮 -- true
            dishTagMapper.saveDishTag(new DishTag(userId,dishId,tagId));
            updateDishTag.setMarkedTag(true);
        }
        return updateDishTag;
    }

    /**
     * 更新菜品评分
     * @author qizong007
     * @date 17:45 2020/11/14
     * @param  userId,dishId,star
     * @return Integer
     **/
    @Override
    public Integer updateDishStar(Integer userId, Integer dishId, Double star) {
        DishComment dishComment = dishCommentMapper.getDishCommentByUserIdDishId(userId, dishId);
        if(dishComment == null){
            // 没有就是新增save
            dishComment = new DishComment();
            dishComment.setStars(star);
            dishComment.setDishId(dishId);
            dishComment.setUserId(userId);
            // 返回值为受影响的行数
            if(dishCommentMapper.saveDishComment(dishComment) != 0){
                return StatusCode.SUCCESS;
            }else{
                return StatusCode.FAIL_TO_SAVE_DISH_STAR;
            }
        }else{
            // 有就是更改update
            dishComment.setStars(star);
            // 为0即更新失败
            if(dishCommentMapper.updateDishComment(dishComment) != 0){
                return StatusCode.SUCCESS;
            }else{
                return StatusCode.FAIL_TO_UPDATE_DISH_STAR;
            }
        }
    }

    /**
     * 获取菜品信息
     * @author qizong007
     * @param dishId
     * @return DishInfo
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
     * 最爱的菜
     * @author qizong007
     * @param userId
     * @return List<DishEntry>
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
     * @return Double
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
     * @return Integer[]
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
