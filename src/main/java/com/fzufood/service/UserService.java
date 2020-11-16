package com.fzufood.service;

import com.fzufood.dto.UserLogin;
import com.fzufood.dto.UserInfo;
import com.fzufood.entity.Tag;
import com.fzufood.dto.DishRecommend;
import java.util.List;

public interface UserService {
    UserLogin login(String code);
    UserInfo getInfo(Integer userId);
    Integer updateInfo( Integer userId,List<Tag> preferredList, List<Tag> avoidList);
    List<DishRecommend> search(String searchName, List<Tag> tagList, Integer canteenId);
    Integer feedback( Integer userId, String content);
}
