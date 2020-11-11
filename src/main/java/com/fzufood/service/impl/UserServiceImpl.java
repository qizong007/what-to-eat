package com.fzufood.service.impl;

import com.fzufood.dto.UserLogin;
import com.fzufood.dto.UserInfo;
import com.fzufood.service.UserService;
import org.springframework.stereotype.Service;
import com.fzufood.entity.Tag;
import com.fzufood.dto.DishRecommend;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserLogin login( String code) {
        return null;
    }
    @Override
    public UserInfo getInfo(String userId)  {
        return null;
    }
    @Override
    public Integer updateInfo( Integer userId,List<Tag> preferredList, List<Tag> avoidList) {
        return null;
    }
    @Override
    public List<DishRecommend> search(String searchName, Integer userId, List<Tag> tagList, Integer canteenId)  {
        return null;
    }
    @Override
    public Integer feedback( Integer userId, String content) {
        return null;
    }
}
