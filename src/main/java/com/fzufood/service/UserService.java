package com.fzufood.service;

import com.fzufood.dto.*;
import com.fzufood.entity.Tag;

import java.util.List;

public interface UserService {
    JsonObject<UserLogin> login(String code);
    JsonObject<UserInfo> getInfo(Integer userId);
    Code updateInfo(Integer userId, List<Tag> preferredList, List<Tag> avoidList);
    JsonObject<List<DishRecommend>> search(String searchName, List<Tag> tagList, Integer canteenId);
    Code feedback(Integer userId, String content);
}
