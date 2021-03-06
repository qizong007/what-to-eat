package com.fzufood.controller;

import com.fzufood.dto.JsonObject;
import com.fzufood.dto.Recommend;
import com.fzufood.dto.WindowEntry;
import com.fzufood.http.Code;
import com.fzufood.http.UpdateMarkedWindowResponse;
import com.fzufood.service.WindowService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/window")
public class WindowController {

    @Autowired
    private WindowService windowService;

    /**
     * 首页窗口推荐接口
     * @param type
     * @param userId
     * @return JsonObject<List<DishRecommend>>
     */
    @ApiOperation("首页窗口推荐接口")
    @GetMapping("/recommend")
    public JsonObject<Recommend> recommend(@RequestParam("type")Integer type,
                                           @RequestParam("userId")Integer userId) throws FileNotFoundException {
       return windowService.recommend(type, userId);
    }

    /**
     * 窗口列表接口
     * @param windowId
     * @param userId
     * @return JsonObject<WindowEntry>
     */
    @ApiOperation("窗口列表接口")
    @GetMapping("/info")
    public JsonObject<WindowEntry> info(@RequestParam("windowId")Integer windowId,
                            @RequestParam("userId")Integer userId) throws FileNotFoundException {
        return windowService.info(windowId, userId);
    }

    /**
     * 获取收藏窗口接口
     * @param userId
     * @return JsonObject<List<DishRecommend>>
     */
    @ApiOperation("获取收藏窗口接口")
    @GetMapping("/getMarkedWindow")
    public JsonObject<Recommend> getMarkedWindow(@RequestParam("userId")Integer userId) throws FileNotFoundException {
        return windowService.getMarkedWindow(userId);
    }

    /**
     * 更新收藏窗口接口
     * @param response
     * @return Code
     */
    @ApiOperation("更新收藏窗口接口")
    @PostMapping("/updateMarkedWindow")
    public Code updateMarkedWindow(@RequestBody UpdateMarkedWindowResponse response) {
        return windowService.updateMarkedWindow(response.getUserId(), response.getWindowId());
    }
}
