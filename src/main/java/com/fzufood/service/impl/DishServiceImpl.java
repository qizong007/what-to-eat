package com.fzufood.service.impl;

import com.fzufood.dto.*;
import com.fzufood.entity.*;
import com.fzufood.http.Code;
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
    @Autowired
    private TagMapper tagMapper;

    /**
     * 更新菜品标签
     * @author qizong007
     * @date 17:42 2020/11/14
     * @param userId,dishId,tagId
     * @return JsonObject<UpdateDishTagResponse>
     **/
    @Override
    public JsonObject<UpdateDishTag> updateDishTag(Integer userId, Integer dishId, Integer tagId) {
        JsonObject<UpdateDishTag> jsonObject = new JsonObject<>();
        UpdateDishTag updateDishTag = new UpdateDishTag();
        if(userId == null || dishId == null || tagId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(updateDishTag);
            return jsonObject;
        }
        DishTag dishTag = null;
        boolean hasTagged = false;
        dishTag = dishTagMapper.getDishTagById(userId, dishId, tagId);
        if(dishTag != null){
            hasTagged = true;
        }
        // 某道菜的某个标签多少人点过
        updateDishTag.setTagName(tagMapper.getTagById(tagId).getContent());
        updateDishTag.setTagId(tagId);
        jsonObject.setCode(StatusCode.SUCCESS);
        if(hasTagged){
            // 用户点过，现在就要取消 -- false
            dishTagMapper.removeDishTagByDishTag(dishTag);
            updateDishTag.setHasTagged(false);
        }else{
            // 用户还未点过，现在要点亮 -- true
            dishTagMapper.saveDishTag(new DishTag(userId,dishId,tagId));
            updateDishTag.setHasTagged(true);
        }
        updateDishTag.setTagNum(dishTagMapper.listDishTagByDishIdAndTagId(dishId, tagId).size());
        jsonObject.setData(updateDishTag);
        return jsonObject;
    }

    /**
     * 更新菜品评分
     * @author qizong007
     * @date 17:45 2020/11/14
     * @param  userId,dishId,star
     * @return Code
     **/
    @Override
    public Code updateDishStar(Integer userId, Integer dishId, Double star) {
        if(userId == null || dishId == null || star == null){
            return new Code(StatusCode.MISSING_PARAMETERS);
        }
        DishComment dishComment = dishCommentMapper.getDishCommentByUserIdDishId(userId, dishId);
        if(dishComment == null){
            // 没有就是新增save
            dishComment = new DishComment();
            dishComment.setStars(star);
            dishComment.setDishId(dishId);
            dishComment.setUserId(userId);
            // 返回值为受影响的行数
            if(dishCommentMapper.saveDishComment(dishComment) != 0){
                return new Code(StatusCode.SUCCESS);
            }else{
                return new Code(StatusCode.FAIL_TO_SAVE_DISH_STAR);
            }
        }else{
            // 有就是更改update
            dishComment.setStars(star);
            // 为0即更新失败
            if(dishCommentMapper.updateDishComment(dishComment) != 0){
                return new Code(StatusCode.SUCCESS);
            }else{
                return new Code(StatusCode.FAIL_TO_UPDATE_DISH_STAR);
            }
        }
    }

    /**
     * 获取菜品信息
     * @author qizong007
     * @param dishId
     * @return JsonObject<DishInfo>
     */
    @Override
    public JsonObject<DishInfo> getDishInfo(Integer dishId,Integer userId) {
        JsonObject<DishInfo> jsonObject = new JsonObject<>();
        DishInfo dishInfo = new DishInfo();
        if(dishId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(dishInfo);
            return jsonObject;
        }
        Dish dish = dishMapper.getDishById(dishId);
        dishInfo.setDishName(dish.getDishName());
        dishInfo.setPrice(dish.getPrice());
        if(dishCommentMapper.getAvgStarsByDishId(dishId) != null){
            dishInfo.setStar(dishCommentMapper.getAvgStarsByDishId(dishId));
        }
        dishInfo.setStarNum(countStarsNumOnDish(dishId));
        dishInfo.setWindowId(dish.getWindow().getWindowId());
        dishInfo.setWindowName(dish.getWindow().getWindowName());
        DishComment dishComment = dishCommentMapper.getDishCommentByUserIdDishId(userId,dishId);
        if(dishComment != null){
            dishInfo.setUserStar(dishComment.getStars());
        }
        List<Integer> tagIds = dishTagMapper.listTagIdsByDishId(dishId);
        boolean[] hasAdded = new boolean[1000];
        if(tagIds != null){
            List<TagInfo> tagInfoList = new ArrayList<>();
            TagInfo tagInfo;
            for(Integer tagId : tagIds){
                Tag tag = tagMapper.getTagById(tagId);
                if(!hasAdded[tagId]){
                    hasAdded[tagId] = true;
                    tagInfo = new TagInfo();
                    tagInfo.setTagId(tag.getTagId());
                    tagInfo.setTagName(tag.getContent());
                    tagInfo.setTagNum(dishTagMapper.listDishTagByDishIdAndTagId(dishId, tagId).size());
                    if(dishTagMapper.getDishTagById(userId,dishId,tag.getTagId()) != null){
                        tagInfo.setHasTagged(true);
                    }
                    tagInfoList.add(tagInfo);
                }
            }
            dishInfo.setTagList(tagInfoList);
        }
        jsonObject.setData(dishInfo);
        jsonObject.setCode(StatusCode.SUCCESS);
        return jsonObject;
    }

    /**
     * 最爱的菜
     * @author qizong007
     * @param userId
     * @return JsonObject<List<DishEntry>>
     */
    @Override
    public JsonObject<Favorites> favorites(Integer userId) {
        JsonObject<Favorites> jsonObject = new JsonObject<>();
        Favorites favorites = new Favorites();
        if(userId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(favorites);
            return jsonObject;
        }
        List<Dish> dishList = userMapper.listLikeDishesById(userId);
        List<DishEntry> dishEntries = new ArrayList<>();
        for(Dish dish : dishList){
            DishEntry dishEntry = new DishEntry();
            dishEntry.setDishId(dish.getDishId());
            dishEntry.setDishName(dish.getDishName());
            dishEntry.setPrice(dish.getPrice());
            dishEntry.setStar(dishCommentMapper.getAvgStarsByDishId(dish.getDishId()));
            dishEntries.add(dishEntry);
        }
        jsonObject.setCode(StatusCode.SUCCESS);
        favorites.setDishList(dishEntries);
        jsonObject.setData(favorites);
        return jsonObject;
    }

    /**
     * 根据dishId，查出该道菜品5种星级分别有几人
     * @author qizong007
     * @param dishId
     * @return Integer[]
     */
    private Integer[] countStarsNumOnDish(Integer dishId){
        List<DishComment> dishComments = dishCommentMapper.listDishCommentsByDishId(dishId);
        Integer[] starNum = new Integer[]{0,0,0,0,0};
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
