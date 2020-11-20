package com.fzufood.service.impl;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.*;
import com.fzufood.entity.*;
import com.fzufood.http.HttpRequest;
import com.fzufood.http.LoginResponseFail;
import com.fzufood.http.LoginResponseSuccess;
import com.fzufood.repository.*;
import com.fzufood.service.UserService;
import com.fzufood.util.StatusCode;
import com.fzufood.util.WeChatAppCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;


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
        UserLogin userLogin = new UserLogin();
        // 缺少参数
        if(code == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(userLogin);
            return jsonObject;
        }
        // 拿前端给的code去请求openId
        String str = HttpRequest.sendGet(WeChatAppCode.URL,
                "appid="+WeChatAppCode.APP_ID
                        +"&secret=" +WeChatAppCode.APP_SECRET
                        +"&js_code="+code
                        +"&grant_type=authorization_code");
        Integer errCode = 0;
        if(str.contains("openid")){
            LoginResponseSuccess responseSuccess = JSON.parseObject(str,LoginResponseSuccess.class);
            String openId = responseSuccess.getOpenid();
            System.out.println(openId);
            User user = userMapper.getUserByOpenId(openId);
            Integer userId;
            if(user == null){
                // 还未注册
                user = new User();
                user.setOpenId(openId);
                System.out.println(user);
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
            System.out.println(userLogin);
            jsonObject.setCode(errCode);
            return jsonObject;
        }else{
            LoginResponseFail responseFail = JSON.parseObject(str,LoginResponseFail.class);
            errCode = responseFail.getErrcode();
            // code无效
            if(errCode == StatusCode.INVALID_CODE){
                jsonObject.setCode(errCode);
                jsonObject.setData(new UserLogin());
                return jsonObject;
            }
            // 系统繁忙
            else if(errCode == StatusCode.SYSTEM_BUSY){
                jsonObject.setCode(errCode);
                jsonObject.setData(userLogin);
                return jsonObject;
            }
            else{
                jsonObject.setData(userLogin);
                jsonObject.setCode(errCode);
                return jsonObject;
            }
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
        UserInfo userInfo = new UserInfo();
        // 缺少参数
        if(userId == null){
            jsonObject.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setData(userInfo);
            return jsonObject;
        }
        List<Tag> preferredList = userMapper.listPreferTagsById(userId);
        List<Tag> avoidList = userMapper.listAvoidTagsById(userId);
        List<Tag> allList = tagMapper.listTags();
        if(preferredList != null){
            userInfo.setPreferredList(preferredList);
        }
        if(avoidList != null){
            userInfo.setAvoidList(avoidList);
        }
        if(allList != null){
            userInfo.setAllList(allList);
        }
        jsonObject.setCode(StatusCode.SUCCESS);
        jsonObject.setData(userInfo);
        return jsonObject;
    }

    private boolean contain(List<Tag> tags, Integer tagId){
        for (Tag tag : tags){
            if (tag.getTagId() == tagId)
                return true;
        }
        return false;
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
        if(userId == null){
            return StatusCode.MISSING_PARAMETERS;
        }
        //对喜好tag的更改
        for(Tag tag : preferredList){
            // 没有就先加tag
            if(tag.getTagId() == null || tagMapper.getTagById(tag.getTagId()) == null){
                if(tagMapper.saveTag(tag) == 0){
                    return StatusCode.FAIL_TO_UPDATE_USER_INFO;
                }
            }
            if (!contain(userMapper.listPreferTagsById(userId),tag.getTagId())){
                if(userMapper.savePreferTag(userId,tag.getTagId()) == 0){
                    return StatusCode.FAIL_TO_UPDATE_USER_INFO;
                }
            }
        }
        for (Tag tag : userMapper.listPreferTagsById(userId)){
            if(!contain(preferredList,tag.getTagId())){
                if(userMapper.removePreferTag(userId,tag.getTagId()) == 0){
                    return StatusCode.FAIL_TO_UPDATE_USER_INFO;
                }
            }
        }
        //对忌口tag的更改
        for(Tag tag : avoidList){
            if(tag.getTagId() == null || tagMapper.getTagById(tag.getTagId()) == null){
                if(tagMapper.saveTag(tag) == 0){
                    return StatusCode.FAIL_TO_UPDATE_USER_INFO;
                }
            }
            if (!contain(userMapper.listAvoidTagsById(userId),tag.getTagId())){
                if(userMapper.saveAvoidTag(userId,tag.getTagId()) == 0){
                    return StatusCode.FAIL_TO_UPDATE_USER_INFO;
                }
            }
        }
        for (Tag tag : userMapper.listAvoidTagsById(userId)){
            if(!contain(avoidList,tag.getTagId())){
                if(userMapper.removeAvoidTag(userId,tag.getTagId()) == 0){
                    return StatusCode.FAIL_TO_UPDATE_USER_INFO;
                }
            }
        }
        return StatusCode.SUCCESS;
    }

    /**
     * 用户搜索
     * @param searchName
     * @param tagList
     * @param canteenId
     * @return JsonObject<List<DishRecommend>>
     * @author invainX
     */
    //TODO:search自定义搜索待实现
    @Override
    public JsonObject<Search> search(String searchName, List<Tag> tagList, Integer canteenId) {

        for (Tag tag : tagList) {
            if (tagMapper.getTagById(tag.getTagId()) == null) {
                tagMapper.saveTag(tag);
            }
        }
        JsonObject<Search> jsonObject = new JsonObject<>();


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
        else {
            List<Dish> dishList = dishMapper.listDishesByName(searchName);
            if (canteenId == null) {
                if (tagList.size() == 0) {
                    for (Dish dish : dishList) {
                        DishRecommend dishRecommend = new DishRecommend();
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
                else if (tagList.size() == 1) {

                    for (Dish dish : dishList) {
                        for (DishTag dishTag : baseDishTags) {
                            if (dish.getDishId() == dishTag.getDishId()) {
                                DishRecommend dishRecommend = new DishRecommend();
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
                            continue;
                        }
                    }
                }else
                {
                    for (Tag tag : tagList) {
                        if (tag == baseTag) continue;
                        List<DishTag> dishTags = dishTagMapper.listDishTagsByTagId(tag.getTagId());
                        for (DishTag dishTag : baseDishTags) {
                            if (!dishTags.contains(dishTag)) {
                                baseDishTags.remove(dishTag);
                            }
                        }
                    }
                    for (Dish dish : dishList) {
                        for (DishTag dishTag : baseDishTags) {
                            if (dish.getDishId() == dishTag.getDishId()) {
                                DishRecommend dishRecommend = new DishRecommend();
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
                            continue;
                        }
                    }
                }
            } else {
                if ((tagList.size() == 0)) {
                    for (Dish dish : dishList) {
                        if (dish.getWindow().getCanteen().getCanteenId() == canteenId) {
                            DishRecommend dishRecommend = new DishRecommend();
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
                } else if(tagList.size() == 1) {
                    for (Dish dish : dishList) {
                        for (DishTag dishTag : baseDishTags) {
                            if (dish.getDishId() == dishTag.getDishId() && dish.getWindow().getCanteen().getCanteenId() == canteenId) {
                                DishRecommend dishRecommend = new DishRecommend();
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
                }else{
                    for (Tag tag : tagList) {
                        if (tag == baseTag) continue;
                        List<DishTag> dishTags = dishTagMapper.listDishTagsByTagId(tag.getTagId());
                        for (DishTag dishTag : baseDishTags) {
                            if (!dishTags.contains(dishTag)) {
                                baseDishTags.remove(dishTag);
                            }
                        }
                    }

                    for (Dish dish : dishList) {
                        for (DishTag dishTag : baseDishTags) {
                            if (dish.getDishId() == dishTag.getDishId() && dish.getWindow().getCanteen().getCanteenId() == canteenId) {
                                DishRecommend dishRecommend = new DishRecommend();
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
        }

        jsonObject.setData(new Search(dishRecommendList));
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