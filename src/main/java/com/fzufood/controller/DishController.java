package com.fzufood.controller;

import com.fzufood.dto.*;
import com.fzufood.http.UpdateDishStarResponse;
import com.fzufood.http.UpdateDishTagResponse;
import com.fzufood.service.DishService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 更新菜品标签接口
     * @param response
     * @return JsonObject<UpdateDishTagResponse>
     */
    @ApiOperation("更新菜品标签接口")
    @PostMapping("/updateDishTag")
    public JsonObject<UpdateDishTag> updateDishTag(@RequestBody UpdateDishTagResponse response){

        return dishService.updateDishTag(response.getUserId(),response.getDishId(),response.getTagId());
    }

    /**
     * 更新菜品评分接口
     * @param response
     * @return Code
     */
    @ApiOperation("更新菜品评分接口")
    @PostMapping("/updateDishStar")
    public Integer updateDishStar(@RequestBody UpdateDishStarResponse response){
        return dishService.updateDishStar(response.getUserId(),response.getDishId(),response.getStar());
    }

    /**
     * 获取菜品信息接口
     * @param dishId
     * @return JsonObject<DishInfo>
     */
    @ApiOperation("获取菜品信息接口")
    @GetMapping("/getDishInfo")
    public JsonObject<DishInfo> getDishInfo(Integer dishId){
        return dishService.getDishInfo(dishId);
    }

    /**
     * 最爱的菜接口
     * @param userId
     * @return JsonObject<List<DishEntry>>
     */
    @ApiOperation("最爱的菜接口")
    @GetMapping("/favorites")
    public JsonObject<Favorites> favorites(Integer userId){
        return dishService.favorites(userId);
    }

}
