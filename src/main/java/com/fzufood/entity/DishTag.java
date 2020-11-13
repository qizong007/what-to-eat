package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜品标签")
public class DishTag {

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("菜品ID")
    private Integer dishId;

    @ApiModelProperty("标签ID")
    private Integer tagId;
}
