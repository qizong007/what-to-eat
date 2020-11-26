package com.fzufood.service;

import com.fzufood.dto.*;
import com.fzufood.entity.Tag;
import com.fzufood.http.Code;
import com.fzufood.http.LoginResponse;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {
    JsonObject<UserLogin> login(LoginResponse code);
    JsonObject<UserInfo> getInfo(Integer userId);
    Code updateInfo(Integer userId, List<Tag> preferredList, List<Tag> avoidList);
    JsonObject<Search> search(String searchName, List<Integer> tagList, Integer canteenId) throws FileNotFoundException;
    Code feedback(Integer userId, String content);
}
