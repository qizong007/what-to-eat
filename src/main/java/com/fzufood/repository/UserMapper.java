package com.fzufood.repository;

import com.fzufood.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author buzhouI
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * 查询所有user(userId, openId, phoneNumber, userName, profilePicture)
     * @return List<User>
     */
    List<User> listUsers();

    /**
     * 根据userId查询对应的user(userId, openId, phoneNumber, userName, profilePicture)
     * @param userId
     * @return User
     */
    User getUserById(Integer userId);

    /**
     * 插入新的user(userId, openId, phoneNumber, userName, profilePicture)
     * @param user
     * @return int
     */
    int saveUser(User user);

    /**
     * 根据userId更新user(userId, openId, phoneNumber, userName, profilePicture)
     * @param user
     * @return int
     */
    int updateUser(User user);

    /**
     * 根据userId删除user(userId, openId, phoneNumber, userName, profilePicture)
     * @param userId
     * @return int
     */
    int removeUser(Integer userId);

    /**
     * 根据userId查询该userId喜欢的所有dish(dishId, dishName, price, byWeight, window)
     * @param userId
     * @return List<Dish>
     */
    List<Dish> listLikeDishesById(Integer userId);

    /**
     * 插入新的likeDish (userId, dishId)
     * @param userId
     * @param dishId
     * @return int
     */
    int saveLikeDish(Integer userId, Integer dishId);

    /**
     * 根据(userId, dishId) 删除likeDish
     * @param userId
     * @param dishId
     * @return int
     */
    int removeLikeDish(Integer userId, Integer dishId);

    /**
     * 根据userId查询该userId收藏的所有(windowId, windowName, location, profile, description, canteen
     * @param userId
     * @return List<Window>
     */
    List<Window> listMarkWindowsById(Integer userId);

    /**
     * 插入新的markWindow (userId, windowId)
     * @param userId
     * @param windowId
     * @return int
     */
    int saveMarkWindow(Integer userId, Integer windowId);

    /**
     * 根据(userId, windowId) 删除markWindow
     * @param userId
     * @param windowId
     * @return int
     */
    int removeMarkWindow(Integer userId, Integer windowId);

    /**
     * 根据userId查询该userId所有feedback(feedbackId, submitTime, content, user)
     * @param userId
     * @return List<Feedback>
     */
    List<Feedback> listMyFeedbackById(Integer userId);

    /**
     * 插入新的myFeedback (userId, feedbackId)
     * @param userId
     * @param feedbackId
     * @return int
     */
    int saveMyFeedback(Integer userId, Integer feedbackId);

    /**
     * 根据(userId, feedbackId) 删除myFeedback
     * @param userId
     * @param feedbackId
     * @return int
     */
    int removeMyFeedback(Integer userId, Integer feedbackId);

    /**
     * 根据userId查询该userId所有偏好的tag(tagId, content)
     * @param userId
     * @return List<Tag>
     */
    List<Tag> listPreferTagsById(Integer userId);

    /**
     * 插入新的preferTag (userId, tagId)
     * @param userId
     * @param tagId
     * @return int
     */
    int savePreferTag(Integer userId, Integer tagId);

    /**
     * 根据(userId, tagId) 删除preferTag
     * @param userId
     * @param tagId
     * @return int
     */
    int removePreferTag(Integer userId, Integer tagId);

    /**
     * 根据userId查询该userId所有忌口tag(tagId, content)
     * @param userId
     * @return List<Tag>
     */
    List<Tag> listAvoidTagsById(Integer userId);

    /**
     * 插入新的avoidTag (userId, tagId)
     * @param userId
     * @param tagId
     * @return int
     */
    int saveAvoidTag(Integer userId, Integer tagId);

    /**
     * 根据(userId, tagId) 删除avoidTag
     * @param userId
     * @param tagId
     * @return int
     */
    int removeAvoidTag(Integer userId, Integer tagId);
}
