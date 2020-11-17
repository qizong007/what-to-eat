package com.fzufood.service;

import com.fzufood.dto.Code;
import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.WindowEntry;


import java.util.List;

public interface WindowService {
    List<DishRecommend> recommend(Integer type, Integer userId);
    WindowEntry info(Integer windowId, Integer userId);
    List<DishRecommend> getMarkedWindow(Integer userId);
    Code updateMarkedWindow(Integer userId, Integer windowId);
}
