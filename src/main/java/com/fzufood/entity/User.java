package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户实体类")
public class User {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户头像")
    private File profilePicture;

    @ApiModelProperty("我的爱心")
    private List<Dish> likes;

    @ApiModelProperty("我的收藏")
    private List<Window> marks;

    @ApiModelProperty("我的反馈")
    private List<Feedback> myFeedback;

    @ApiModelProperty("我的偏好")
    private List<Tag> preferTags;

    @ApiModelProperty("我的忌口")
    private List<Tag> avoidTags;


}
