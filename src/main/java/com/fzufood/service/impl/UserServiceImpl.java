package com.fzufood.service.impl;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.*;
import com.fzufood.entity.*;
import com.fzufood.http.HttpRequest;
import com.fzufood.http.LoginResponse;
import com.fzufood.repository.*;
import com.fzufood.service.UserService;
import com.fzufood.util.StatusCode;
import com.fzufood.util.WeChatAppCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private TagMapper tagMapper;

    /**
     * 用户登录
     * @author qizong007
     * @date 19:31 2020/11/17
     * @param code
     * @return JsonObject<UserLogin>
     **/
    @Override
    public JsonObject<UserLogin> login(String code) {
        JsonObject<UserLogin> jsonObject = new JsonObject<>();
        // 缺少参数
        if(code == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(null);
            return jsonObject;
        }
        UserLogin userLogin = new UserLogin();
        // 拿前端给的code去请求openId
        String str = HttpRequest.sendGet(WeChatAppCode.URL,
                "appid="+WeChatAppCode.APP_ID
                        +"&secret=SECRET" +WeChatAppCode.APP_SECRET
                        +"&js_code="+code
                        +"&grant_type=authorization_code");
        LoginResponse response = JSON.parseObject(str,LoginResponse.class);
        // 看看有没有错
        Integer errCode = response.getErrcode();
        // code无效
        if(errCode == StatusCode.INVALID_CODE){
            System.out.println(response.getErrmsg());
            jsonObject.setCode(StatusCode.INVALID_CODE);
            jsonObject.setData(null);
            return jsonObject;
        }
        // 系统繁忙
        else if(errCode == StatusCode.SYSTEM_BUSY){
            System.out.println(response.getErrmsg());
            jsonObject.setCode(StatusCode.SYSTEM_BUSY);
            jsonObject.setData(null);
            return jsonObject;
        }
        else if(errCode == StatusCode.SUCCESS){
            String openId = response.getOpenid();
            User user = userMapper.getUserByOpenId(openId);
            Integer userId;
            if(user == null){
                // 还未注册
                user = new User();
                user.setOpenId(openId);
                userMapper.saveUser(user);
                userId = userMapper.getUserByOpenId(openId).getUserId();
                userLogin.setUserId(userId);
                userLogin.setHasRegistered(false);
            }else{
                // 已注册
                userId = user.getUserId();
                userLogin.setUserId(userId);
                userLogin.setHasRegistered(true);
            }
            jsonObject.setData(userLogin);
            jsonObject.setCode(StatusCode.SUCCESS);
            return jsonObject;
        }
        else{
            jsonObject.setData(null);
            jsonObject.setCode(StatusCode.UNKNOWN_LOGIN_FAULT);
            return jsonObject;
        }
    }

    /**
     * 获取用户信息
     * @param userId
     * @return JsonObject<UserInfo>
     * @author qizong007
     * @date 14:11 2020/11/15
     **/
    @Override
    public JsonObject<UserInfo> getInfo(Integer userId) {
        JsonObject<UserInfo> jsonObject = new JsonObject<>();
        // 缺少参数
        if(userId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(null);
            return jsonObject;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPreferTags(userMapper.listPreferTagsById(userId));
        userInfo.setAvoidTags(userMapper.listAvoidTagsById(userId));
        jsonObject.setCode(StatusCode.SUCCESS);
        jsonObject.setData(userInfo);
        return jsonObject;
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
    public Integer updateInfo(Integer userId, List<Tag> preferredList, List<Tag> avoidList) {
        User user = userMapper.getUserById(userId);
        user.setPreferTags(preferredList);
        user.setAvoidTags(avoidList);
        if (userMapper.updateUser(user) != 0) {
            return StatusCode.SUCCESS;
        } else {
            return StatusCode.FAIL_TO_UPDATE_USER_INFO;
        }
    }

    /**
     * 用户搜索
     * @param searchName
     * @param tagList
     * @param canteenId
     * @return JsonObject<List<DishRecommend>>
     * @author invainX
     */
    @Override
    public JsonObject<List<DishRecommend>> search(String searchName, List<Tag> tagList, Integer canteenId) {

        JsonObject<List<DishRecommend>> jsonObject = new JsonObject<>();
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

        jsonObject.setData(dishRecommendList);
        jsonObject.setCode(StatusCode.SUCCESS);
        return jsonObject;

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
    public Integer feedback(Integer userId, String content) {
        if(userId == null || content == null){
            return StatusCode.MISSING_PARAMETERS;
        }
        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setSubmitTime(new Timestamp(new Date().getTime()));
        feedback.setUser(userMapper.getUserById(userId));
        if (feedbackMapper.saveFeedback(feedback) != 0) {
            return StatusCode.SUCCESS;
        } else {
            return StatusCode.FAIL_TO_SAVE_FEEDBACK;
        }
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
}