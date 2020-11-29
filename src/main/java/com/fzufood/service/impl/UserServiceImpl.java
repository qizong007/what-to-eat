package com.fzufood.service.impl;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.*;
import com.fzufood.entity.*;
import com.fzufood.http.*;
import com.fzufood.repository.*;
import com.fzufood.service.UserService;
import com.fzufood.service.WindowService;
import com.fzufood.util.PicturePath;
import com.fzufood.util.StatusCode;
import com.fzufood.util.WeChatAppCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
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
    @Autowired
    private WindowService windowService;

    /**
     * 用户登录
     * @author qizong007
     * @date 19:31 2020/11/17
     * @param code
     * @return JsonObject<UserLogin>
     **/
    @Override
    public JsonObject<UserLogin> login(LoginResponse code) {
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
                        +"&js_code="+code.getCode()
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
            System.out.println("openId为"+openId+"的用户已登录");
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
    public Code updateInfo(Integer userId, List<Tag> preferredList, List<Tag> avoidList) {
        if(userId == null){
            return new Code(StatusCode.MISSING_PARAMETERS);
        }
        //对喜好tag的更改
        for(Tag tag : preferredList){
            // 没有就先加tag
            if(tag.getTagId() == null || tagMapper.getTagById(tag.getTagId()) == null){
                if(tagMapper.saveTag(tag) == 0){
                    return new Code(StatusCode.FAIL_TO_UPDATE_USER_INFO);
                }
            }
            if (!contain(userMapper.listPreferTagsById(userId),tag.getTagId())){
                if(userMapper.savePreferTag(userId,tag.getTagId()) == 0){
                    return new Code(StatusCode.FAIL_TO_UPDATE_USER_INFO);
                }
            }
        }
        for (Tag tag : userMapper.listPreferTagsById(userId)){
            if(!contain(preferredList,tag.getTagId())){
                if(userMapper.removePreferTag(userId,tag.getTagId()) == 0){
                    return new Code(StatusCode.FAIL_TO_UPDATE_USER_INFO);
                }
            }
        }
        //对忌口tag的更改
        for(Tag tag : avoidList){
            if(tag.getTagId() == null || tagMapper.getTagById(tag.getTagId()) == null){
                if(tagMapper.saveTag(tag) == 0){
                    return new Code(StatusCode.FAIL_TO_UPDATE_USER_INFO);
                }
            }
            if (!contain(userMapper.listAvoidTagsById(userId),tag.getTagId())){
                if(userMapper.saveAvoidTag(userId,tag.getTagId()) == 0){
                    return new Code(StatusCode.FAIL_TO_UPDATE_USER_INFO);
                }
            }
        }
        for (Tag tag : userMapper.listAvoidTagsById(userId)){
            if(!contain(avoidList,tag.getTagId())){
                if(userMapper.removeAvoidTag(userId,tag.getTagId()) == 0){
                    return new Code(StatusCode.FAIL_TO_UPDATE_USER_INFO);
                }
            }
        }
        return new Code(StatusCode.SUCCESS);
    }

    /**
     * 配置dish推荐
     * @author qizong007
     * @date 13:42 2020/11/27
     * @param
     * @return
     **/
    private void setDishRecommend(List<DishRecommend> dishRecommendList, DishRecommend dishRecommend,Window window) throws FileNotFoundException {
        dishRecommend.setWindowId(window.getWindowId());
        dishRecommend.setWindowName(window.getWindowName());
        dishRecommend.setPngSrc(windowService.getWindowPngSrc(window.getWindowId()));
        dishRecommend.setCanteenName(windowMapper.getWindowById(window.getWindowId()).getCanteen().getCanteenName());
        if(dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()) != null){
            dishRecommend.setStar(dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()));
        }
        List<Dish> dishList = windowMapper.listDishesById(window.getWindowId());
        if(dishList.size()>3){
            dishRecommend.setDish(dishList.subList(0,3));
        }else{
            dishRecommend.setDish(dishList);
        }
        dishRecommendList.add(dishRecommend);
    }

    /**
     * 用户搜索
     * @author qizong007
     * @date 17:18 2020/11/29
     * @param
     * @return
     **/
    @Override
    public JsonObject<Search> search(String searchName, List<Integer> tagList, Integer canteenId) throws FileNotFoundException {
        JsonObject<Search> jsonObject = new JsonObject<>();
        List<DishRecommend> dishRecommendList = new ArrayList<>();
        if(searchName == null || searchName == ""){
            if(canteenId == null || canteenId == 0){
                List<Canteen> canteenList = canteenMapper.listCanteens();
                for(Canteen canteen : canteenList){
                    List<Window> windowList = canteenMapper.listWindowsById(canteen.getCanteenId());
                    for(Window window : windowList){
                        DishRecommend dishRecommend = new DishRecommend();
                        setDishRecommend(dishRecommendList,dishRecommend,window);
                    }
                }
            }else{
                List<Window> windowList = canteenMapper.listWindowsById(canteenId);
                for(Window window : windowList){
                    DishRecommend dishRecommend = new DishRecommend();
                    setDishRecommend(dishRecommendList,dishRecommend,window);
                }
            }
        }else{
            List<Window> windowList = dishMapper.listWindowsByDishName(searchName);
            DishRecommend dishRecommend;
            for(Window window : windowList){
                dishRecommend = new DishRecommend();
                dishRecommend.setWindowId(window.getWindowId());
                dishRecommend.setWindowName(window.getWindowName());
                dishRecommend.setPngSrc(windowService.getWindowPngSrc(window.getWindowId()));
                dishRecommend.setCanteenName(window.getCanteen().getCanteenName());
                if(dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()) != null){
                    dishRecommend.setStar(dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()));
                }
                List<Dish> dishList = windowMapper.listDishesById(window.getWindowId());
                if(dishList.size()>3){
                    dishRecommend.setDish(dishList.subList(0,3));
                }else{
                    dishRecommend.setDish(dishList);
                }
                dishRecommendList.add(dishRecommend);
            }
            if(canteenId != null && canteenId > 0){
                List<DishRecommend> newDishRecommends = new ArrayList<>();
                for(DishRecommend dishRecommend1 : dishRecommendList){
                    if(windowMapper.getWindowById(dishRecommend1.getWindowId()).getCanteen().getCanteenId() == canteenId){
                        newDishRecommends.add(dishRecommend1);
                    }
                }
                dishRecommendList = newDishRecommends;
            }
        }
        if(tagList != null && tagList.size() != 0){
            Map<Integer,Tag> tagMap = new HashMap<>();
            for(Integer tagId : tagList){
                tagMap.put(tagId,tagMapper.getTagById(tagId));
            }
            List<DishRecommend> newDishRecommendList = new ArrayList<>();
            for(DishRecommend dishRecommend : dishRecommendList){
                List<Dish> dishList = dishRecommend.getDish();
                label:for(Dish dish : dishList){
                    List<Integer> tagIds = dishTagMapper.listTagIdsByDishId(dish.getDishId());
                    for(Integer tagId : tagIds){
                        if(tagMap.get(tagId) != null){
                            newDishRecommendList.add(dishRecommend);
                            break label;
                        }
                    }
                }
            }
            dishRecommendList = newDishRecommendList;
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
    public Code feedback(Integer userId, String content) {
        if(userId == null || content == null){
            return new Code(StatusCode.MISSING_PARAMETERS);
        }
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