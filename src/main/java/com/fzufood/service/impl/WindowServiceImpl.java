package com.fzufood.service.impl;

import com.fzufood.dto.*;
import com.fzufood.entity.*;
import com.fzufood.http.Code;
import com.fzufood.repository.*;
import com.fzufood.service.WindowService;
import com.fzufood.util.PicturePath;
import com.fzufood.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
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
     *
     * @param type,userId
     * @return JsonObject<List < DishRecommend>>
     * @author gaoyichao33
     */
    @Override
    public JsonObject<Recommend> recommend(Integer type, Integer userId) throws FileNotFoundException {
        JsonObject<Recommend> jsonObject = new JsonObject<>();
        Recommend recommend = new Recommend();
        if (userId == null) {
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(recommend);
            return jsonObject;
        }
        List<DishRecommend> dishRecommends = new ArrayList<>();
        if (type == 1) {
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
                            if (tag.getTagId().equals(avoid.getTagId())) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 1) {
                            break;
                        }
                    }
                    if (flag == 1) {
                        continue;
                    }
                    Window window = dish.getWindow();
                    dishRecommend.setWindowId(window.getWindowId());
                    dishRecommend.setWindowName(window.getWindowName());
                    dishRecommend.setPngSrc(getWindowPngSrc(window.getWindowId()));
                    if (!window.getDescription().equals("")) {
                        dishRecommend.setDescription(window.getDescription());
                    }
                    dishRecommend.setCanteenName(windowMapper.getWindowById(window.getWindowId()).getCanteen().getCanteenName());
                    //dishRecommend.setStar(countCanteenStar(windowMapper.getWindowById(window.getWindowId()).getCanteen().getCanteenId()));
                    if (dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()) != null) {
                        dishRecommend.setStar(dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()));
                    }
                    List<Dish> dishList = windowMapper.listDishesById(window.getWindowId());
                    if (dishList.size() > 3) {
                        dishRecommend.setDish(dishList.subList(0, 3));
                    } else {
                        dishRecommend.setDish(dishList);
                    }
                    dishRecommends.add(dishRecommend);
                }
            }
            List<DishRecommend> newList = new ArrayList<>();
            for (DishRecommend cd : dishRecommends) {
                if (!newList.contains(cd)) {
                    newList.add(cd);
                }
            }
            jsonObject.setCode(StatusCode.SUCCESS);
            if (newList.size() > 50) {
                recommend.setWindowList(newList.subList(0, 50));
            } else {
                recommend.setWindowList(newList);
            }
            jsonObject.setData(recommend);
        } else if (type == 2 || type == 3) {
            return popular(userId);
        }
        return jsonObject;
    }

    /**
     * 热门窗口
     *
     * @param userId
     * @return JsonObject<List < DishRecommend>>
     * @author gaoyichao33
     */
    public JsonObject<Recommend> popular(Integer userId) throws FileNotFoundException {
        JsonObject<Recommend> jsonObject = new JsonObject<>();
        Recommend recommend = new Recommend();
        if (userId == null) {
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(recommend);
            return jsonObject;
        }
        List<DishRecommend> dishRecommends = new ArrayList<>();
        List<Window> windowList = windowMapper.listWindows();
        windowList.sort(new Comparator<Window>() {
            Integer tagnum1;
            //Double i1;
            BigDecimal i1;
            Integer tagnum2;
            //Double i2;
            BigDecimal i2;
            Double avgStarsByWindowId;

            @Override
            public int compare(Window window1, Window window2) {
                Integer window1Id = window1.getWindowId();
                tagnum1 = dishTagMapper.countTagNumByWindowId(window1Id);
                avgStarsByWindowId = dishCommentMapper.getAvgStarsByWindowId(window1.getWindowId());
                if (avgStarsByWindowId != null) {
                    i1 = new BigDecimal(avgStarsByWindowId * 0.6 + tagnum1 * 0.4);
                } else {
                    i1 = BigDecimal.ZERO;
                }
                Integer window2Id = window2.getWindowId();
                tagnum2 = dishTagMapper.countTagNumByWindowId(window2Id);
                avgStarsByWindowId = dishCommentMapper.getAvgStarsByWindowId(window2.getWindowId());
                if (avgStarsByWindowId != null) {
                    i2 = new BigDecimal(avgStarsByWindowId * 0.6 + tagnum2 * 0.4);
                } else {
                    i2 = BigDecimal.ZERO;
                }
                //return tagnum2.compareTo(tagnum1);
                return i2.compareTo(i1);
            }
        });
        for (Window window : windowList) {
            DishRecommend dishRecommend = new DishRecommend();
            dishRecommend.setWindowId(window.getWindowId());
            dishRecommend.setWindowName(window.getWindowName());
            dishRecommend.setPngSrc(getWindowPngSrc(window.getWindowId()));
            if (!window.getDescription().equals("")) {
                dishRecommend.setDescription(window.getDescription());
            }
            dishRecommend.setStar(dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()));
            List<Dish> dishList = windowMapper.listDishesById(window.getWindowId());
            dishRecommend.setDish(dishList);
            dishRecommend.setCanteenName(windowMapper.getWindowById(window.getWindowId()).getCanteen().getCanteenName());
            if (dishList.size() > 3) {
                dishRecommend.setDish(dishList.subList(0, 3));
            } else {
                dishRecommend.setDish(dishList);
            }
            dishRecommends.add(dishRecommend);
        }
        jsonObject.setCode(StatusCode.SUCCESS);
        recommend.setWindowList(dishRecommends.subList(0, 15));
        jsonObject.setData(recommend);
        return jsonObject;
    }

    @Override
    public JsonObject<WindowEntry> info(Integer windowId, Integer userId) throws FileNotFoundException {
        JsonObject<WindowEntry> jsonObject = new JsonObject<>();
        WindowEntry windowEntry = new WindowEntry();
        if (userId == null) {
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(windowEntry);
            return jsonObject;
        }
        List<Dish> dishList = windowMapper.listDishesById(windowId);
        List<Tag> tagList = new ArrayList<>();
        windowEntry.setWindowId(windowMapper.getWindowById(windowId).getWindowId());
        windowEntry.setWindowName(windowMapper.getWindowById(windowId).getWindowName());
        windowEntry.setPngSrc(getWindowPngSrc(windowId));
        if (windowMapper.getWindowById(windowId).getDescription() != null) {
            windowEntry.setDescription(windowMapper.getWindowById(windowId).getDescription());
        }
        windowEntry.setMapSrc(getWindowMapSrc(windowId));
        windowEntry.setCanteenName(windowMapper.getWindowById(windowId).getCanteen().getCanteenName());
        if (dishCommentMapper.getAvgStarsByWindowId(windowId) != null) {
            windowEntry.setStar(dishCommentMapper.getAvgStarsByWindowId(windowId));
        }
        for (Dish dish : dishList) {
            List<Tag> tags = dishMapper.listTagsById(dish.getDishId());
            for (Tag tag : tags) {
                if (!tagList.contains(tag)) {
                    tagList.add(tag);
                }
            }
            dish.setTags(tags);
            if (dishCommentMapper.getAvgStarsByDishId(dish.getDishId()) != null) {
                dish.setStar(dishCommentMapper.getAvgStarsByDishId(dish.getDishId()));
            }
        }
        windowEntry.setTags(tagList);
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        windowEntry.setIsMarked(windowList.contains(windowMapper.getWindowById(windowId)));
        windowEntry.setDishes(dishList);
        jsonObject.setCode(StatusCode.SUCCESS);
        jsonObject.setData(windowEntry);
        return jsonObject;
    }

    /**
     * 获取收藏窗口
     *
     * @param userId
     * @return JsonObject<List < DishRecommend>>
     * @author gaoyichao33
     */
    @Override
    public JsonObject<Recommend> getMarkedWindow(Integer userId) throws FileNotFoundException {
        JsonObject<Recommend> jsonObject = new JsonObject<>();
        Recommend recommend = new Recommend();
        if (userId == null) {
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(recommend);
            return jsonObject;
        }
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        List<DishRecommend> dishRecommends = new ArrayList<>();
        DishRecommend dishRecommend;
        for (Window window : windowList) {
            dishRecommend = new DishRecommend();
            dishRecommend.setWindowId(window.getWindowId());
            dishRecommend.setWindowName(window.getWindowName());
            dishRecommend.setCanteenName(window.getCanteen().getCanteenName());
            dishRecommend.setPngSrc(getWindowPngSrc(window.getWindowId()));
            dishRecommend.setDescription(window.getDescription());
            if (dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()) != null) {
                dishRecommend.setStar(dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()));
            }
            List<Dish> dishList = windowMapper.listDishesById(window.getWindowId());
            if (dishList.size() > 3) {
                dishRecommend.setDish(dishList.subList(0, 3));
            } else {
                dishRecommend.setDish(dishList);
            }
            dishRecommends.add(dishRecommend);
        }
        jsonObject.setCode(StatusCode.SUCCESS);
        recommend.setWindowList(dishRecommends);
        jsonObject.setData(recommend);
        return jsonObject;
    }

    /**
     * 更新收藏窗口
     *
     * @param userId,windowId
     * @return Integer
     * @author gaoyichao33
     */
    @Override
    public Code updateMarkedWindow(Integer userId, Integer windowId) {
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        int flag = 1;
        for (Window window : windowList) {
            if (window.getWindowId() == windowId) {
                userMapper.removeMarkWindow(userId, windowId);
                flag = 0;
                break;
            }
        }
        if (flag == 1) {
            userMapper.saveMarkWindow(userId, windowId);
        }
        if (windowMapper.updateWindow(windowMapper.getWindowById(windowId)) != 0) {
            return new Code(StatusCode.SUCCESS);
        } else {
            return new Code(StatusCode.FAIL_TO_UPDATE_MARKED_WINDOW);
        }
    }

    /**
     * 获取窗口图片路径
     *
     * @param windowId
     * @return String
     * @author qizong007
     * @date 21:31 2020/11/23
     **/
    public String getWindowPngSrc(Integer windowId) throws FileNotFoundException {
        Window window = windowMapper.getWindowById(windowId);
        String windowName = window.getWindowName();
        Integer canteenId = window.getCanteen().getCanteenId();
        if (canteenId == 2 || canteenId == 8) {
            return PicturePath.PREFIX + canteenId + "/" + windowName + ".JPG";
        } else {
            return PicturePath.PREFIX + canteenId + "/" + windowName + ".jpg";
        }
    }

    /**
     * 获取窗口地图
     *
     * @param windowId
     * @return String
     * @author qizong007
     * @date 14:31 2020/11/27
     **/
    public String getWindowMapSrc(Integer windowId) throws FileNotFoundException {
        Window window = windowMapper.getWindowById(windowId);
        String windowName = window.getWindowName();
        Integer canteenId = window.getCanteen().getCanteenId();
        return PicturePath.PREFIX + "map/" + canteenId + "/" + windowName + ".png";
    }

//    /**
//     * 计算食堂星级
//     * @author qizong007
//     * @date 21:47 2020/11/26
//     * @param
//     * @return
//     **/
//    private Double countCanteenStar(Integer canteenId){
//        List<Window> windowList = canteenMapper.listWindowsById(canteenId);
//        double star = 0;
//        for(Window window : windowList){
//            star += dishCommentMapper.getAvgStarsByWindowId(window.getWindowId());
//        }
//        return star/windowList.size();
//    }
}
