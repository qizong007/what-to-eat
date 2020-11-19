package com.fzufood.service.impl;

import com.fzufood.dto.*;
import com.fzufood.entity.*;
import com.fzufood.repository.*;
import com.fzufood.service.WindowService;
import com.fzufood.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WindowServiceImpl implements WindowService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishTagMapper dishTagMapper;
    @Autowired
    private WindowMapper windowMapper;
    @Autowired
    private DishCommentMapper dishCommentMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 首页推荐窗口
     * @author gaoyichao33
     * @param type,userId
     * @return JsonObject<List<DishRecommend>>
     */
    @Override
    public JsonObject<Recommend> recommend(Integer type, Integer userId) {
        JsonObject<Recommend> jsonObject = new JsonObject<>();
        if(userId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(null);
            return jsonObject;
        }
        List<DishRecommend> dishRecommends = new ArrayList<>();
        if(type==1) {
            List<Tag> preferList = userMapper.listPreferTagsById(userId);
            List<Tag> avoidList = userMapper.listAvoidTagsById(userId);

            for (Tag prefer : preferList) {
                List<DishTag> dishTagList = dishTagMapper.listDishTagsByTagId(prefer.getTagId());
                for (DishTag dishTag : dishTagList) {
                    DishRecommend dishRecommend = new DishRecommend();
                    Dish dish = dishMapper.getDishById(dishTag.getDishId());
                    int flag = 0;
                    for (Tag avoid : avoidList) {
                        List<Tag> tagList = dishMapper.listTagsById(dishTag.getDishId());
                        for (Tag tag : tagList) {
                            if (tag.getTagId() == avoid.getTagId()) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 1)
                            break;
                    }
                    if (flag == 1)
                        continue;
                    Window window = dish.getWindow();
                    dishRecommend.setWindowId(window.getWindowId());
                    dishRecommend.setWindowName(window.getWindowName());
                    dishRecommend.setPngSrc(window.getProfileURI());
                    dishRecommend.setDescription(window.getDescription());
                    dishRecommend.setStar(countStarsOnWindow(window.getWindowId()));
                    dishRecommend.setDish(window.getDishes());
                    dishRecommends.add(dishRecommend);
                }
            }
            List<DishRecommend> newList = new  ArrayList<>();
            for (DishRecommend cd:dishRecommends) {
                if(!newList.contains(cd)){
                    newList.add(cd);
                }
            }
            jsonObject.setCode(StatusCode.SUCCESS);
            jsonObject.setData(new Recommend(newList));
        }
        else if(type==2){
            return popular(userId);
        }
        return jsonObject;
    }

    /**
     * 热门窗口
     *@author gaoyichao33
     * @param userId
     * @return JsonObject<List<DishRecommend>>
     */
    public JsonObject<Recommend> popular(Integer userId){
        JsonObject<Recommend> jsonObject = new JsonObject<>();
        if(userId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(null);
            return jsonObject;
        }
        List<DishRecommend> dishRecommends = new ArrayList<>();
        List<Window> windowList = windowMapper.listWindows();
        System.out.println("sort start");
        Collections.sort(windowList, new Comparator<Window>() {
            public int compare(Window window1, Window window2) {
                double tagnum1 = countTagNumOnWindow(window1.getWindowId());
                double i1 = countStarsOnWindow(window1.getWindowId())*0.6+tagnum1*0.4;
                System.out.println(i1);
                double tagnum2 = countTagNumOnWindow(window2.getWindowId());
                double i2 = countStarsOnWindow(window2.getWindowId())*0.6+tagnum2*0.4;
                System.out.println(i2);
                double diff = i2 - i1;
                if(diff > 0){
                    return 1;
                }else if(diff < 0){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        System.out.println("sort over");
        for(Window window : windowList){
            System.out.println(window);
            DishRecommend dishRecommend = new DishRecommend();
            dishRecommend.setWindowId(window.getWindowId());
            dishRecommend.setWindowName(window.getWindowName());
            dishRecommend.setPngSrc(window.getProfileURI());
            dishRecommend.setDescription(window.getDescription());
            dishRecommend.setStar(countStarsOnWindow(window.getWindowId()));
            dishRecommend.setDish(window.getDishes());
            dishRecommends.add(dishRecommend);
        }
        jsonObject.setCode(StatusCode.SUCCESS);
        jsonObject.setData(new Recommend(dishRecommends));
        return jsonObject;
    }
    /**
     * 根据windowId得出窗口tag数量
     * @author gaoyichao33
     * @param windowId
     * @return int
     */
    private int countTagNumOnWindow(Integer windowId){
        List<Dish> dishList = windowMapper.listDishesById(windowId);
        List<Tag> tagLists = new ArrayList<>();
        for(Dish dish : dishList){
            List<Tag> tagList= dishMapper.listTagsById(dish.getDishId());
            tagLists.addAll(tagList);
        }
        List<Tag> newList = new ArrayList<>();
        for (Tag cd:tagLists) {
            if(!newList.contains(cd)){
                newList.add(cd);
            }
        }
        return newList.size();
    }
    @Override
    public JsonObject<WindowEntry> info(Integer windowId, Integer userId) {
        JsonObject<WindowEntry> jsonObject = new JsonObject<>();
        if(userId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(null);
            return jsonObject;
        }
        WindowEntry windowEntry = new WindowEntry();
        List<Dish> dishList =  windowMapper.listDishesById(windowId);
        List<Tag> tagList = new ArrayList<>();
        windowEntry.setWindowId(windowMapper.getWindowById(windowId).getWindowId());
        windowEntry.setWindowName(windowMapper.getWindowById(windowId).getWindowName());
        windowEntry.setPngSrc(windowMapper.getWindowById(windowId).getProfileURI());
        windowEntry.setDescription(windowMapper.getWindowById(windowId).getDescription());
        windowEntry.setMapSrc(windowMapper.getWindowById(windowId).getLocationURI());
        windowEntry.setCanteenName(windowMapper.getWindowById(windowId).getCanteen().getCanteenName());
        windowEntry.setStar(countStarsOnWindow(windowId));
        for (Dish dish : dishList){
            List<Tag> tags = dishMapper.listTagsById(dish.getDishId());
            for(Tag tag : tags){
                if(!tagList.contains(tag)){
                    tagList.add(tag);
                }
            }
            dish.setTags(tags);
            dish.setStar(countStarsOnDish(dish.getDishId()));
        }
        windowEntry.setTags(tagList);
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        windowEntry.setIsMarked(windowList.contains(windowId));
        windowEntry.setDishes(dishList);
        jsonObject.setCode(StatusCode.SUCCESS);
        jsonObject.setData(windowEntry);
        return jsonObject;

    }

    /**
     * 获取收藏窗口
     * @author gaoyichao33
     * @param userId
     * @return JsonObject<List<DishRecommend>>
     */
    @Override
    public JsonObject<Recommend> getMarkedWindow(Integer userId) {
        JsonObject<Recommend> jsonObject = new JsonObject<>();
        if(userId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(null);
            return jsonObject;
        }
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        List<DishRecommend> dishRecommends = new ArrayList<>();
        DishRecommend dishRecommend;
        for(Window window : windowList){
            dishRecommend = new DishRecommend();
            dishRecommend.setWindowId(window.getWindowId());
            dishRecommend.setWindowName(window.getWindowName());
            dishRecommend.setPngSrc(window.getProfileURI());
            dishRecommend.setDescription(window.getDescription());
            dishRecommend.setStar(countStarsOnWindow(window.getWindowId()));
            dishRecommend.setDish(windowMapper.listDishesById(window.getWindowId()));
            dishRecommends.add(dishRecommend);
        }
        jsonObject.setCode(StatusCode.SUCCESS);
        jsonObject.setData(new Recommend(dishRecommends));
        return jsonObject;
    }

    /**
     * 根据windowId，查出该窗口星级
     * @author gaoyichao33
     * @param windowId
     * @return Double
     */
    private Double countStarsOnWindow(Integer windowId){
        List<Dish> dishList = windowMapper.listDishesById(windowId);
        Double stars = 0.0;
        for(Dish dish : dishList){
            stars += countStarsOnDish(dish.getDishId());
        }
        if(dishList.size() == 0)
            return 0.0;
        else
            return stars/dishList.size();
    }

    private Double countStarsOnDish(Integer dishId){
        List<DishComment> dishComments = dishCommentMapper.listDishCommentsByDishId(dishId);
        Double stars = 0.0;
        for(DishComment dishComment : dishComments){
            stars += dishComment.getStars();
        }
        if(dishComments.size() == 0)
            return 0.0;
        else
            return stars/dishComments.size();
    }

    /**
     * 更新收藏窗口
     * @author gaoyichao33
     * @param userId,windowId
     * @return Integer
     */
    @Override
    public Integer updateMarkedWindow(Integer userId, Integer windowId)  {
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        int flag=1;
        for(Window window : windowList){
            if(window.getWindowId()==windowId){
                userMapper.removeMarkWindow(userId,windowId);
                flag = 0;
                break;
            }
        }
        if(flag==1)
        {
            userMapper.saveMarkWindow(userId,windowId);
        }

        if(windowMapper.updateWindow(windowMapper.getWindowById(windowId)) != 0){
            return StatusCode.SUCCESS;
        }else {
            return StatusCode.FAIL_TO_UPDATE_MARKED_WINDOW;
    }

}
}
