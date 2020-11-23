package com.fzufood.service;

import com.fzufood.dto.JsonObject;
import com.fzufood.dto.Recommend;
import com.fzufood.dto.WindowEntry;
import com.fzufood.http.Code;

import java.io.FileNotFoundException;

public interface WindowService {
    JsonObject<Recommend> recommend(Integer type, Integer userId) throws FileNotFoundException;
    JsonObject<WindowEntry> info(Integer windowId, Integer userId) throws FileNotFoundException;
    JsonObject<Recommend> getMarkedWindow(Integer userId) throws FileNotFoundException;
    Code updateMarkedWindow(Integer userId, Integer windowId);
    String getWindowPngSrc(Integer windowId) throws FileNotFoundException;
}
