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
    private Long commentId;

    @ApiModelProperty("评价星级")
    private Double stars;

    @ApiModelProperty("计数器")
    private Integer count;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("菜品ID")
    private Long dishId;

}
