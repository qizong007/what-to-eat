package com.fzufood.controller;

import com.fzufood.dto.*;
import com.fzufood.entity.Tag;
import com.fzufood.http.*;
import com.fzufood.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     * @param code 要拿着这个东西去查真正的openId,返回我们自己的userid
     * @return JsonObject<UserLogin>
     */
    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public JsonObject<UserLogin> login(@RequestBody LoginResponse code){
        return userService.login(code);
    }

    /**
     * 获取用户信息接口
     * @param userId
     * @return JsonObject<UserInfo>
     */
    @ApiOperation("获取用户信息接口")
    @GetMapping("/getInfo")
    public JsonObject<UserInfo> getInfo(@RequestParam("userId")Integer userId){
        return userService.getInfo(userId);
    }

    /**
     * 获取用户信息接口
     * @param response
     * @return Code
     */
    @ApiOperation("获取用户信息接口")
    @PostMapping("/updateInfo")
    public Code updateInfo(@RequestBody UpdateInfoResponse response){
        return userService.updateInfo(response.getUserId(),response.getPreferredList(),response.getAvoidList() );
    }

//    /**
//     * 用户搜索接口
//     * @param searchName
//     * @param tagList
//     * @param canteenId
//     * @return JsonObject<List<DishRecommend>>
//     */
    @ApiOperation("用户搜索接口")
    @GetMapping("/search")
    public JsonObject<Search> search() throws FileNotFoundException {
        return userService.search(null,null,null);
    }

    /**
     * 用户反馈接口
     * @param response
     * @return Code
     */
    @ApiOperation("用户反馈接口")
    @PostMapping("/feedback")
    public Code feedback(@RequestBody FeedbackResponse response){
        return userService.feedback(response.getUserId(),response.getContent());
    }

}
