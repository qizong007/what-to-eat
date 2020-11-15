package com.fzufood.controller;

import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.UserInfo;
import com.fzufood.dto.UserLogin;
import com.fzufood.entity.Tag;
import com.fzufood.service.UserService;
import com.fzufood.util.StatusCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     * @param code 	要拿着这个东西去查真正的openId,返回我们自己的userid
     * @return
     */
    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public UserLogin login(@RequestParam("code") String code){
        // TODO
        return new UserLogin(1438,true);
    }

    /**
     * 获取用户信息接口
     * @param userId
     * @return
     */
    @ApiOperation("获取用户信息接口")
    @GetMapping("/getInfo")
    public UserInfo getInfo(Integer userId){
        return userService.getInfo(userId);
    }

    /**
     * 获取用户信息接口
     * @param userId
     * @return
     */
    @ApiOperation("获取用户信息接口")
    @PostMapping("/updateInfo")
    public Integer updateInfo(@RequestParam("userId") Integer userId,
                               @RequestParam("preferredList") List<Tag> preferredList,
                               @RequestParam("avoidList") List<Tag> avoidList){
        // TODO
        return StatusCode.SUCCESS;
    }

    /**
     * 用户搜索接口
     * @param searchName
     * @param userId
     * @param tagList
     * @param canteenId
     * @return
     */
    @ApiOperation("用户搜索接口")
    @GetMapping("/search")
    public List<DishRecommend> search(String searchName, Integer userId, List<Tag> tagList, Integer canteenId){
        //TODO
        DishRecommend dishRecommend = new DishRecommend(1,"百味园",null,"干锅贼香","玫瑰园二楼",4.3,null);
        List<DishRecommend> dishRecommendList = new ArrayList<>();
        dishRecommendList.add(dishRecommend);
        return dishRecommendList;
    }

    /**
     * 用户反馈接口
     * @param userId
     * @param content
     * @return
     */
    @ApiOperation("用户反馈接口")
    @PostMapping("/feedback")
    public Integer feedback(@RequestParam("userId") Integer userId,
                            @RequestParam("content") String content){
        return userService.feedback(userId,content);
    }

}
