package com.fzufood.service;

import com.fzufood.dto.*;
import com.fzufood.entity.Tag;

import java.util.List;

public interface UserService {
    JsonObject<UserLogin> login(String code);
    JsonObject<UserInfo> getInfo(Integer userId);
    Integer updateInfo(Integer userId, List<Tag> preferredList, List<Tag> avoidList,List<Tag> allList);
    JsonObject<Search> search(String searchName, List<Tag> tagList, Integer canteenId);
    Integer feedback(Integer userId, String content);
}
