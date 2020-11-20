package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜品评价")
public class DishComment {

    @ApiModelProperty("菜品评价ID")
    private Integer commentId = -1;

    @ApiModelProperty("评价星级")
    private Double stars = 0.0;

    @ApiModelProperty("用户ID")
    private Integer userId = -1;

    @ApiModelProperty("菜品ID")
    private Integer dishId = -1;

}
