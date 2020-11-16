package com.fzufood.repository;

import com.fzufood.entity.Feedback;
import com.fzufood.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class FeedbackMapperTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    void listFeedbackTest() {
        List<Feedback> feedback = feedbackMapper.listFeedback();
        for(Feedback f: feedback) {
            System.out.println(f);
        }
    }

    @Test
    void getFeedbackByIdTest() {
        Feedback feedback = feedbackMapper.getFeedbackById(1);
        System.out.println(feedback);
    }

    @Test
    void saveFeedbackTest() {
        Feedback feedback = new Feedback();
        User user = new User();
        user.setUserId(1);

        feedback.setContent("saveFeedbackTest");
        feedback.setSubmitTime(new Timestamp(new Date().getTime()));
        feedback.setUser(user);
        feedbackMapper.saveFeedback(feedback);
    }

    @Test
    void updateFeedbackByIdTest() {
        Feedback feedback = new Feedback();
        User user = new User();
        user.setUserId(1);

        feedback.setFeedbackId(19);
        feedback.setContent("updateFeedbackTest");
        feedback.setSubmitTime(new Timestamp(new Date().getTime()));
        feedback.setUser(user);
        feedbackMapper.updateFeedbackById(feedback);
    }

    @Test
    void removeFeedbackByIdTest(){
        feedbackMapper.removeFeedbackById(19);
    }
}
