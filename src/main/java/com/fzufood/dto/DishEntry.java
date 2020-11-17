package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishEntry extends Code{

    private Integer dishId;
    private String dishName;
    private BigDecimal price;
    private Double star;

}
