package com.fzufood.service;

import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.WindowEntry;


import java.util.List;

public interface WindowService {
    DishRecommend recommend(Integer type, Integer userId);
    List<WindowEntry> info(Integer windowId, Integer userId);
    List<WindowEntry> getMarkedWindow(Integer userId);
    Integer updateMarkedWindow( Integer userId, Integer windowId);
}
