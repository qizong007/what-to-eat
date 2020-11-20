package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜品")
public class Dish {

    @ApiModelProperty("菜品ID")
    private Integer dishId = -1;

    @ApiModelProperty("菜品名")
    private String dishName = "";

    @ApiModelProperty("菜品价格")
    private BigDecimal price = new BigDecimal(0);

    @ApiModelProperty("是否按重量计算")
    private Boolean byWeight = false;

    @ApiModelProperty("所属的窗口")
    private Window window = new Window();

    @ApiModelProperty("菜品的星级")
    private Double star = 0.0;

    @ApiModelProperty("菜品标签")
    private List<Tag> tags = new ArrayList<>();
}
