package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜品")
public class Dish {

    @ApiModelProperty("菜品ID")
    private Integer dishId;

    @ApiModelProperty("菜品名")
    private String dishName;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("是否按重量计算")
    private Boolean byWeight;

    @ApiModelProperty("菜品标签")
    private List<Tag> tags;

}
