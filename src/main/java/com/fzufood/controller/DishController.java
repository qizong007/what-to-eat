package com.fzufood.controller;

import com.fzufood.dto.*;
import com.fzufood.service.DishService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 更新菜品标签接口
     * @param userId
     * @param dishId
     * @param tagId
     * @return JsonObject<UpdateDishTag>
     */
    @ApiOperation("更新菜品标签接口")
    @PostMapping("/updateDishTag")
    public JsonObject<UpdateDishTag> updateDishTag(@RequestParam("userId") Integer userId,
                                                   @RequestParam("dishId")Integer dishId,
                                                   @RequestParam("tagId")Integer tagId){

        return dishService.updateDishTag(userId, dishId, tagId);
    }

    /**
     * 更新菜品评分接口
     * @param userId
     * @param dishId
     * @param star
     * @return Code
     */
    @ApiOperation("更新菜品评分接口")
    @PostMapping("/updateDishStar")
    public Integer updateDishStar(@RequestParam("userId") Integer userId,
                               @RequestParam("dishId")Integer dishId,
                               @RequestParam("star")Double star){
        return dishService.updateDishStar(userId, dishId, star);
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
    public JsonObject<List<DishEntry>> favorites(Integer userId){
        return dishService.favorites(userId);
    }

}
