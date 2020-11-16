package com.fzufood.service.impl;

import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.WindowEntry;
import com.fzufood.entity.Window;
import com.fzufood.repository.UserMapper;
import com.fzufood.service.WindowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class WindowServiceImpl implements WindowService {

    @Override
    public DishRecommend recommend(Integer type, Integer userId) {
        return null;
    }
    @Override
    public List<WindowEntry> info(Integer windowId, Integer userId) {
        return null;
    }
    @Override
    public List<WindowEntry> getMarkedWindow(Integer userId) {
        return null;
    }
    @Override
    public Integer updateMarkedWindow( Integer userId, Integer windowId)  {
        return null;
    }
}
