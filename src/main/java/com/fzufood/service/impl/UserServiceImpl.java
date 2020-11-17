package com.fzufood.service.impl;

import com.fzufood.dto.Code;
import com.fzufood.dto.UserLogin;
import com.fzufood.dto.UserInfo;
import com.fzufood.entity.*;
import com.fzufood.repository.*;
import com.fzufood.service.UserService;
import com.fzufood.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fzufood.dto.DishRecommend;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private CanteenMapper canteenMapper;
    @Autowired
    private WindowMapper windowMapper;
    @Autowired
    private DishTagMapper dishTagMapper;
    @Autowired
    private DishCommentMapper dishCommentMapper;
    @Override
    public UserLogin login(String code) {
        return null;
    }

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
     * 获取用户信息
     * @param userId
     * @return UserInfo
     * @author qizong007
     * @date 14:11 2020/11/15
     **/
    @Override
    public UserInfo getInfo(Integer userId) {
        UserInfo userInfo = new UserInfo();
        if(userId == null){
            userInfo.setCode(StatusCode.MISSING_PARAMETERS);
            return userInfo;
        }
        userInfo.setCode(StatusCode.SUCCESS);
        userInfo.setPreferTags(userMapper.listPreferTagsById(userId));
        userInfo.setAvoidTags(userMapper.listAvoidTagsById(userId));
        return userInfo;
    }

    /**
     * 更新用户信息
     * @param userId
     * @param preferredList
     * @param avoidList
     * @return Code
     * @author invainX
     * @date 15:50 2020/11/15
     */
    @Override
    public Code updateInfo(Integer userId, List<Tag> preferredList, List<Tag> avoidList) {
        User user = userMapper.getUserById(userId);
        user.setPreferTags(preferredList);
        user.setAvoidTags(avoidList);
        if (userMapper.updateUser(user) != 0) {
            return new Code(StatusCode.SUCCESS);
        } else {
            return new Code(StatusCode.FAIL_TO_UPDATE_USER_INFO);
        }
    }

    /**
     * 用户搜索
     * @param searchName
     * @param tagList
     * @param canteenId
     * @return List<DishRecommend>
     * @author invainX
     */
    @Override
    public List<DishRecommend> search(String searchName, List<Tag> tagList, Integer canteenId) {

        //TODO:search自定义搜索待实现



        List<DishRecommend> dishRecommendList = new ArrayList<>();
        Tag baseTag = tagList.get(0);
        List<DishTag> baseDishTags = dishTagMapper.listDishTagsByTagId(baseTag.getTagId());


        if (searchName == null) {
            if (canteenId == null) {
                if(tagList.size() == 0){
                    //TODO:null null null返回最热门的五个
                }
                else if (tagList.size() == 1) {
                    for (DishTag dishTag : baseDishTags) {
                        DishRecommend dishRecommend = new DishRecommend();
                        Dish dish = dishMapper.getDishById(dishTag.getDishId());
                        dishRecommend.setWindowId(dish.getWindow().getWindowId());
                        dishRecommend.setWindowName(dish.getWindow().getWindowName());
                        dishRecommend.setPngSrc(dish.getWindow().getProfileURI());
                        dishRecommend.setDescription(dish.getWindow().getDescription());
                        dishRecommend.setCanteenName(dish.getWindow().getCanteen().getCanteenName());
                        //星级计算
                        dishRecommend.setStar(countStarsOnWindow(dish.getWindow().getWindowId()));
                        //获取窗口的dishes
                        dishRecommend.setDish(windowMapper.listDishesById(dish.getWindow().getWindowId()));
                        dishRecommendList.add(dishRecommend);
                    }

                } else {
                    for (Tag tag : tagList) {
                        if (tag == baseTag) continue;
                        List<DishTag> dishTags = dishTagMapper.listDishTagsByTagId(tag.getTagId());
                        for (DishTag dishTag : baseDishTags) {
                            if (!dishTags.contains(dishTag)) {
                                baseDishTags.remove(dishTag);
                            }
                        }
                    }
                    for (DishTag dishTag : baseDishTags) {
                        DishRecommend dishRecommend = new DishRecommend();
                        Dish dish = dishMapper.getDishById(dishTag.getDishId());
                        dishRecommend.setWindowId(dish.getWindow().getWindowId());
                        dishRecommend.setWindowName(dish.getWindow().getWindowName());
                        dishRecommend.setPngSrc(dish.getWindow().getProfileURI());
                        dishRecommend.setDescription(dish.getWindow().getDescription());
                        dishRecommend.setCanteenName(dish.getWindow().getCanteen().getCanteenName());
                        //星级计算
                        dishRecommend.setStar(countStarsOnWindow(dish.getWindow().getWindowId()));
                        //获取窗口的dishes
                        dishRecommend.setDish(windowMapper.listDishesById(dish.getWindow().getWindowId()));
                        dishRecommendList.add(dishRecommend);
                    }


                }
            } else {
                List<Window> windows = canteenMapper.listWindowsById(canteenId);
                if (tagList.size() == 0){
                    for(Window window : windows){
                        DishRecommend dishRecommend = new DishRecommend();
                        dishRecommend.setWindowId(window.getWindowId());
                        dishRecommend.setWindowName(window.getWindowName());
                        dishRecommend.setPngSrc(window.getProfileURI());
                        dishRecommend.setDescription(window.getDescription());
                        dishRecommend.setCanteenName(window.getCanteen().getCanteenName());
                        //窗口星级计算
                        dishRecommend.setStar(countStarsOnWindow(window.getWindowId()));
                        //获取窗口的dishes
                        dishRecommend.setDish(window.getDishes());
                        dishRecommendList.add(dishRecommend);
                    }
                }
                else if (tagList.size() == 1) {
                    for (DishTag dishTag : baseDishTags) {
                        DishRecommend dishRecommend = new DishRecommend();
                        Dish dish = dishMapper.getDishById(dishTag.getDishId());
                        if (windows.contains(dish.getWindow().getWindowId())) {
                            dishRecommend.setWindowId(dish.getWindow().getWindowId());
                            dishRecommend.setWindowName(dish.getWindow().getWindowName());
                            dishRecommend.setPngSrc(dish.getWindow().getProfileURI());
                            dishRecommend.setDescription(dish.getWindow().getDescription());
                            dishRecommend.setCanteenName(dish.getWindow().getCanteen().getCanteenName());
                            //星级计算
                            dishRecommend.setStar(countStarsOnWindow(dish.getWindow().getWindowId()));
                            //获取窗口的dishes
                            dishRecommend.setDish(windowMapper.listDishesById(dish.getWindow().getWindowId()));
                            dishRecommendList.add(dishRecommend);
                        }
                    }

                } else {
                    for (Tag tag : tagList) {
                        if (tag == baseTag) continue;
                        List<DishTag> dishTags = dishTagMapper.listDishTagsByTagId(tag.getTagId());
                        for (DishTag dishTag : baseDishTags) {
                            if (!dishTags.contains(dishTag)) {
                                baseDishTags.remove(dishTag);
                            }
                        }
                    }
                    for (DishTag dishTag : baseDishTags) {
                        DishRecommend dishRecommend = new DishRecommend();
                        Dish dish = dishMapper.getDishById(dishTag.getDishId());
                        if (windows.contains(dish.getWindow().getWindowId())) {
                            dishRecommend.setWindowId(dish.getWindow().getWindowId());
                            dishRecommend.setWindowName(dish.getWindow().getWindowName());
                            dishRecommend.setPngSrc(dish.getWindow().getProfileURI());
                            dishRecommend.setDescription(dish.getWindow().getDescription());
                            dishRecommend.setCanteenName(dish.getWindow().getCanteen().getCanteenName());
                            //星级计算
                            dishRecommend.setStar(countStarsOnWindow(dish.getWindow().getWindowId()));
                            //获取窗口的dishes
                            dishRecommend.setDish(windowMapper.listDishesById(dish.getWindow().getWindowId()));
                            dishRecommendList.add(dishRecommend);
                        }
                    }


                }
            }




        }
        return dishRecommendList;

    }

    /**
     * 用户反馈
     * @param userId
     * @param content
     * @return Code
     * @author qizong007
     * @date 14:09 2020/11/15
     **/
    @Override
    public Code feedback(Integer userId, String content) {
        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setSubmitTime(new Timestamp(new Date().getTime()));
        feedback.setUser(userMapper.getUserById(userId));
        if (feedbackMapper.saveFeedback(feedback) != 0) {
            return new Code(StatusCode.SUCCESS);
        } else {
            return new Code(StatusCode.FAIL_TO_SAVE_FEEDBACK);
        }
    }
}