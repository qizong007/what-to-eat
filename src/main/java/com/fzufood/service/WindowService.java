package com.fzufood.service;

import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.WindowEntry;


import java.util.List;

public interface WindowService {
    List<DishRecommend> recommend(Integer type, Integer userId);
    WindowEntry info(Integer windowId, Integer userId);
    List<DishRecommend> getMarkedWindow(Integer userId);
    Integer updateMarkedWindow( Integer userId, Integer windowId);
}
