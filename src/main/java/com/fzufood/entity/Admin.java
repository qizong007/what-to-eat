package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qizong007
 * @create 2020/11/22 21:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("管理员")
public class Admin {
    @ApiModelProperty("管理员ID")
    private Integer adminId = -1;
    @ApiModelProperty("管理员用户名")
    private String adminName = "";
    @ApiModelProperty("管理员密码")
    private String adminPassword = "";
}
