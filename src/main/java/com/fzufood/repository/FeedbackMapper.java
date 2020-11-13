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
     * 插入新的feedback(feedbackId, submitTime, content, user)
     * @param feedback
     */
    void saveFeedback(Feedback feedback);

    /**
     * 根据feedbackId更新feedback(feedbackId, submitTime, content, user)
     * @param feedback
     */
    void updateFeedback(Feedback feedback);

    /**
     * 根据feedbackId删除feedback(feedbackId, submitTime, content, user)
     * @param feedbackId
     */
    void removeFeedbackById(Integer feedbackId);
}
