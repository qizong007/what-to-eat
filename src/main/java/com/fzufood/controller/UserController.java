package com.fzufood.controller;

import com.fzufood.dto.Code;
import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.UserInfo;
import com.fzufood.dto.UserLogin;
import com.fzufood.entity.Tag;
import com.fzufood.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     * @param code 	要拿着这个东西去查真正的openId,返回我们自己的userid
     * @return UserLogin
     */
    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public UserLogin login(@RequestParam("code") String code){
        return userService.login(code);
    }

    /**
     * 获取用户信息接口
     * @param userId
     * @return UserInfo
     */
    @ApiOperation("获取用户信息接口")
    @GetMapping("/getInfo")
    public UserInfo getInfo(@RequestParam("userId")Integer userId){
        return userService.getInfo(userId);
    }

    /**
     * 获取用户信息接口
     * @param userId
     * @return Code
     */
    @ApiOperation("获取用户信息接口")
    @PostMapping("/updateInfo")
    public Code updateInfo(@RequestParam("userId") Integer userId,
                           @RequestParam("preferredList") List<Tag> preferredList,
                           @RequestParam("avoidList") List<Tag> avoidList){
        return userService.updateInfo(userId, preferredList, avoidList);
    }

    /**
     * 用户搜索接口
     * @param searchName
     * @param tagList
     * @param canteenId
     * @return
     */
    @ApiOperation("用户搜索接口")
    @GetMapping("/search")
    public List<DishRecommend> search(@RequestParam("searchName")String searchName,
                                      @RequestParam("tagList")List<Tag> tagList,
                                      @RequestParam("canteenId")Integer canteenId){
        return userService.search(searchName, tagList, canteenId);
    }

    /**
     * 用户反馈接口
     * @param userId
     * @param content
     * @return Code
     */
    @ApiOperation("用户反馈接口")
    @PostMapping("/feedback")
    public Code feedback(@RequestParam("userId") Integer userId,
                         @RequestParam("content") String content){
        return userService.feedback(userId,content);
    }

}
