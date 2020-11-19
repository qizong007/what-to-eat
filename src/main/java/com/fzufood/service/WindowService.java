package com.fzufood.service;

import com.fzufood.dto.JsonObject;
import com.fzufood.dto.Recommend;
import com.fzufood.dto.WindowEntry;

public interface WindowService {
    JsonObject<Recommend> recommend(Integer type, Integer userId);
    JsonObject<WindowEntry> info(Integer windowId, Integer userId);
    JsonObject<Recommend> getMarkedWindow(Integer userId);
    Integer updateMarkedWindow(Integer userId, Integer windowId);
}
