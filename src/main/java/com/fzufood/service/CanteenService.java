package com.fzufood.service;

import com.fzufood.dto.JsonObject;
import com.fzufood.http.CrowdedResponse;

/**
 * @Author qizong007
 * @create 2020/11/26 17:04
 */
public interface CanteenService {
    JsonObject<CrowdedResponse> crowded();
}
