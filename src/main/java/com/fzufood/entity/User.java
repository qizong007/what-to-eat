package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户实体类")
public class User {

    @ApiModelProperty("用户ID")
    private Integer userId = -1;

    @ApiModelProperty("用户openID")
    private String openId = "";

}
