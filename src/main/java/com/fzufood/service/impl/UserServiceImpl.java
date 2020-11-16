package com.fzufood.service.impl;

import com.fzufood.dto.UserLogin;
import com.fzufood.dto.UserInfo;
import com.fzufood.entity.*;
import com.fzufood.repository.*;
import com.fzufood.service.UserService;
import com.fzufood.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fzufood.dto.DishRecommend;

import javax.swing.*;
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
    private TagMapper tagMapper;
    @Autowired
    private WindowMapper windowMapper;
    @Autowired
    private DishTagMapper dishTagMapper;
    @Override
    public UserLogin login( String code) {
        return null;
    }

    /**
     * 获取用户信息
     * @author qizong007
     * @date 14:11 2020/11/15
     * @param userId
     * @return Integer
     **/
    @Override
    public UserInfo getInfo(Integer userId)  {
        return new UserInfo(userMapper.listPreferTagsById(userId),userMapper.listAvoidTagsById(userId));
    }

    /**
     * 更新用户信息
     * @author invainX
     * @date 15:50 2020/11/15
     * @param userId preferredList avoidList
     * @return Integer
     */
    @Override
    public Integer updateInfo(Integer userId,List<Tag> preferredList, List<Tag> avoidList) {
        User user = userMapper.getUserById(userId);
        user.setPreferTags(preferredList);
        user.setAvoidTags(avoidList);
        if(userMapper.updateUser(user) != 0){
            return StatusCode.SUCCESS;
        }else {
            return StatusCode.FAIL_TO_UPDATE_USER_INFO;
        }
    }

    /**
     * 用户搜索
     * @author invainX
     * @param searchName,tagList,canteenId
     * @return List<DishRecommend>
     */
    @Override
    public List<DishRecommend> search(String searchName, List<Tag> tagList, Integer canteenId){

        //TODO:search自定义搜索待实现


        List<DishRecommend> dishRecommendList = new ArrayList<>();
        Tag baseTag = tagList.get(0);
        List<DishTag> baseDishTags = dishTagMapper.listDishTagsByTagId(baseTag.getTagId());
        List<Dish> searchedDishList = null;
        if(tagList.size() == 1){

            for(DishTag dishTag:baseDishTags){
                searchedDishList.add(dishMapper.getDishById(dishTag.getDishId()));
            }

            DishRecommend dishRecommend = new DishRecommend();
            dishRecommend.setDish(searchedDishList);
            Canteen canteen = canteenMapper.getCanteenById(canteenId);



            dishRecommendList.add(dishRecommend);






        }else{
            for(Tag tag: tagList){
                if(tag == baseTag) continue;
                List<DishTag> dishTags = dishTagMapper.listDishTagsByTagId(tag.getTagId());
                for(DishTag dishTag:baseDishTags){
                    if(!dishTags.contains(dishTag)){
                        baseDishTags.remove(dishTag);
                    }
                }


            }
        }




        return null;
    }

    /**
     * 用户反馈
     * @author qizong007
     * @date 14:09 2020/11/15
     * @param userId,content
     * @return Integer
     **/
    @Override
    public Integer feedback(Integer userId, String content) {
        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setSubmitTime(new Date());
        feedback.setUser(userMapper.getUserById(userId));
        if(feedbackMapper.saveFeedback(feedback) != 0){
            return StatusCode.SUCCESS;
        }else{
            return StatusCode.FAIL_TO_SAVE_FEEDBACK;
        }
    }
}
