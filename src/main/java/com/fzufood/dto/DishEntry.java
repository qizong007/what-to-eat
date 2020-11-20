package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishEntry{

    private Integer dishId = -1;
    private String dishName = "";
    private BigDecimal price = new BigDecimal(0);
    private Double star = 0.0;

}
