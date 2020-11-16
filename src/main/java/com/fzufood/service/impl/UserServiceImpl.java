package com.fzufood.service.impl;

import com.fzufood.dto.UserLogin;
import com.fzufood.dto.UserInfo;
import com.fzufood.entity.Feedback;
import com.fzufood.entity.User;
import com.fzufood.repository.*;
import com.fzufood.service.UserService;
import com.fzufood.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fzufood.entity.Tag;
import com.fzufood.dto.DishRecommend;

import java.sql.Timestamp;
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
     * @data 15:50 2020/11/15
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
     * @data
     * @param searchName userId tagList canteenId
     * @return List<DishRecommend>
     */
    @Override
    public List<DishRecommend> search(String searchName, Integer userId, List<Tag> tagList, Integer canteenId){
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
        feedback.setSubmitTime(new Timestamp(0));
        feedback.setUser(userMapper.getUserById(userId));
        if(feedbackMapper.saveFeedback(feedback) != 0){
            return StatusCode.SUCCESS;
        }else{
            return StatusCode.FAIL_TO_SAVE_FEEDBACK;
        }
    }
}
