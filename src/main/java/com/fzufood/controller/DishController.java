package com.fzufood.controller;

import com.fzufood.dto.DishEntry;
import com.fzufood.dto.DishInfo;
import com.fzufood.dto.UpdateDishTag;
import com.fzufood.service.DishService;
import com.fzufood.util.StatusCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
     * @return
     */
    @ApiOperation("更新菜品标签接口")
    @PostMapping("/updateDishTag")
    public UpdateDishTag updateDishTag(@RequestParam("userId") Integer userId,
                                       @RequestParam("dishId")Integer dishId,
                                       @RequestParam("tagId")Integer tagId){
        return new UpdateDishTag("空心菜",3,20,true);
    }

    /**
     * 更新菜品评分接口
     * @param userId
     * @param dishId
     * @param star
     * @return
     */
    @ApiOperation("更新菜品评分接口")
    @PostMapping("/updateDishStar")
    public Integer updateDishStar(@RequestParam("userId") Integer userId,
                                       @RequestParam("dishId")Integer dishId,
                                       @RequestParam("star")Double star){
        return StatusCode.SUCCESS;
    }

    /**
     * 获取菜品信息接口
     * @param dishId
     * @return
     */
    @ApiOperation("获取菜品信息接口")
    @GetMapping("/getDishInfo")
    public DishInfo getDishInfo(Integer dishId){
        Integer[] arr = {0, 0, 0, 1, 19};
        return new DishInfo("空心菜",new BigDecimal("1.5"),5d,4.9,arr,null);
    }

    /**
     * 最爱的菜接口
     * @param userId
     * @return
     */
    @ApiOperation("最爱的菜接口")
    @GetMapping("/favorites")
    public List<DishEntry> favorites(Integer userId){
        return null;
    }

}
