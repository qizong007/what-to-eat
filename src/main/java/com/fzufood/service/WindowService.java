package com.fzufood.service;

import com.fzufood.dto.Code;
import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.JsonObject;
import com.fzufood.dto.WindowEntry;


import java.util.List;

public interface WindowService {
    JsonObject<List<DishRecommend>> recommend(Integer type, Integer userId);
    JsonObject<WindowEntry> info(Integer windowId, Integer userId);
    JsonObject<List<DishRecommend>> getMarkedWindow(Integer userId);
    Code updateMarkedWindow(Integer userId, Integer windowId);
}
