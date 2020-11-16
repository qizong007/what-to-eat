package com.fzufood.repository;

import com.fzufood.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author buzhouI
 */
@Repository
@Mapper
public interface FeedbackMapper {

    /**
     * 查询所有feedback(feedbackId, submitTime, content, user)
     * @return List<Feedback>
     */
    List<Feedback> listFeedback();

    /**
     * 根据feedbackId查询对应的feedback(feedbackId, submitTime, content, user)
     * @param feedbackId
     * @return Feedback
     */
    Feedback getFeedbackById(Integer feedbackId);

    /**
     * 插入新的feedback(user, submitTime, content) 只使用了user中的userId
     * @param feedback
     * @return int
     */
    int saveFeedback(Feedback feedback);

    /**
     * 根据feedbackId更新feedback(feedbackId, submitTime, content, user)
     * @param feedback
     * @return int
     */
    int updateFeedbackById(Feedback feedback);

    /**
     * 根据feedbackId删除feedback(feedbackId, submitTime, content, user)
     * @param feedbackId
     * @return int
     */
    int removeFeedbackById(Integer feedbackId);
}
