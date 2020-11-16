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
@ApiModel("窗口实体类")
public class Window {

    @ApiModelProperty("窗口ID")
    private Integer windowId;

    @ApiModelProperty("窗口名")
    private String windowName;

    @ApiModelProperty("窗口位置")
    private String locationURI;

    @ApiModelProperty("店面图片")
    private String profileURI;

    @ApiModelProperty("窗口介绍")
    private String description;

    @ApiModelProperty("所属的餐厅")
    private Canteen canteen;

    @ApiModelProperty("菜品")
    private List<Dish> dishes;
}
