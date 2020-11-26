package com.fzufood.controller;

import com.fzufood.dto.JsonObject;
import com.fzufood.http.CrowdedResponse;
import com.fzufood.service.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author qizong007
 * @create 2020/11/26 16:49
 */
@RestController
@RequestMapping("/canteen")
public class CanteenController {

    @Autowired
    private CanteenService canteenService;

    @RequestMapping("/crowded")
    public JsonObject<CrowdedResponse> crowded(){
        return canteenService.crowded();
    }
}
