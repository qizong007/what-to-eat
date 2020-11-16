package com.fzufood.controller;

import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.WindowEntry;
import com.fzufood.util.StatusCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/window")
public class WindowController {

    /**
     * 首页窗口推荐接口
     * @param type
     * @param userId
     * @return
     */
    @ApiOperation("首页窗口推荐接口")
    @GetMapping("/recommend")
    public DishRecommend recommend(Integer type,Integer userId){
        //TODO
        return new DishRecommend(1,"百味园",null,"干锅贼香","玫瑰园二楼",4.3,null);
    }

    /**
     * 窗口列表接口
     * @param windowId
     * @param userId
     * @return
     */
    @ApiOperation("窗口列表接口")
    @GetMapping("/info")
    public List<WindowEntry> info(Integer windowId, Integer userId) {
        return null;
    }

    /**
     * 获取收藏窗口接口
     * @param userId
     * @return
     */
    @ApiOperation("获取收藏窗口接口")
    @GetMapping("/getMarkedWindow")
    public List<WindowEntry> getMarkedWindow(Integer userId) {
        return null;
    }

    /**
     * 更新收藏窗口接口
     * @param userId
     * @param windowId
     * @return
     */
    @ApiOperation("更新收藏窗口接口")
    @PostMapping("/updateMarkedWindow")
    public Integer updateMarkedWindow(@RequestParam("userId") Integer userId,
                                      @RequestParam("windowId")Integer windowId) {
        return StatusCode.SUCCESS;
    }
}
