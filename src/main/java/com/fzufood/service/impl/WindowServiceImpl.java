package com.fzufood.service.impl;

import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.WindowEntry;
import com.fzufood.service.WindowService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class WindowServiceImpl implements WindowService {
    @Override
    public DishRecommend dishRecommend(Integer type, Integer userId) {
        return null;
    }
    @Override
    public List<WindowEntry> list(Integer windowId, Integer userId) {
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
