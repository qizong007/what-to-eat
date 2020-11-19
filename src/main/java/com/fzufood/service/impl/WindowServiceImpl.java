package com.fzufood.service.impl;

import com.fzufood.dto.*;
import com.fzufood.entity.*;
import com.fzufood.repository.*;
import com.fzufood.service.WindowService;
import com.fzufood.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public JsonObject<List<DishRecommend>> recommend(Integer type, Integer userId) {
        JsonObject<List<DishRecommend>> jsonObject = new JsonObject<>();
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
            jsonObject.setData(newList);
        }
        else if(type==2){
            List<DishRecommend> recommendwindow = popular(userId).getData();
            jsonObject.setCode(StatusCode.SUCCESS);
            jsonObject.setData(recommendwindow);
        }
        return jsonObject;
    }
    /**
     * 热门窗口
     *@author gaoyichao33
     * @param userId
     * @return JsonObject<List<DishRecommend>>
     */
    @Override
    public  JsonObject<List<DishRecommend>> popular(Integer userId){
        JsonObject<List<DishRecommend>> jsonObject = new JsonObject<>();
        if(userId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(null);
            return jsonObject;
        }
        List<DishRecommend> dishRecommends = new  ArrayList<>();
        List<Window> windowList = windowMapper.listWindows();
        Collections.sort(windowList, new Comparator<Window>()
        {
            public int compare(Window window1, Window window2)
            {
                double  tagnum1=countTagnumOnWindow(window1.getWindowId());
                double i1 = countStarsOnWindow(window1.getWindowId())*0.6+tagnum1*0.4;
                double  tagnum2=countTagnumOnWindow(window2.getWindowId());
                double i2 = countStarsOnWindow(window2.getWindowId())*0.6+tagnum2*0.4;
                int flag = new Double(i2-i1).intValue();
                return flag;
            }
        });
        for(Window window : windowList){
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
        jsonObject.setData(dishRecommends);
        return jsonObject;
    }
    /**
     * 根据windowId得出窗口tag数量
     * @author gaoyichao33
     * @param windowId
     * @return int
     */
    private int countTagnumOnWindow(Integer windowId){
        List<Dish> dishList = windowMapper.listDishesById(windowId);
        int num;
        List<Tag> tagLists = new ArrayList<>();
        for(Dish dish : dishList){
            List<Tag> tagList= dishMapper.listTagsById(dish.getDishId());
            tagLists.addAll(tagList);
        }
        List<Tag> newList = new  ArrayList<>();
        for (Tag cd:tagLists) {
            if(!newList.contains(cd)){
                newList.add(cd);
            }
        }
        return  newList.size();
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
    public JsonObject<List<DishRecommend>> getMarkedWindow(Integer userId) {
        JsonObject<List<DishRecommend>> jsonObject = new JsonObject<>();
        if(userId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(null);
            return jsonObject;
        }
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        List<DishRecommend> dishRecommends = new ArrayList<>();
        for(Window window : windowList){
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
        jsonObject.setData(dishRecommends);
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
        return stars/dishList.size();
    }

    private Double countStarsOnDish(Integer dishId){
        List<DishComment> dishComments = dishCommentMapper.listDishCommentsByDishId(dishId);
        Double stars = 0.0;
        for(DishComment dishComment : dishComments){
            stars += dishComment.getStars();
        }
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
        User user = userMapper.getUserById(userId);
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        Window  window = windowMapper.getWindowById(windowId);
        windowList.add(window);
        //user.setMarkWindows(windowList);
        if(windowMapper.updateWindow(window) != 0){
            return StatusCode.SUCCESS;
        }else {
            return StatusCode.FAIL_TO_UPDATE_MARKED_WINDOW;
    }

}
}
