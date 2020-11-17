package com.fzufood.dto;

import com.fzufood.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishInfo extends Code{

    private String dishName;
    private BigDecimal price;
    private Double userStar;
    private Double star;
    private Integer[] starNum;
    private List<Tag> tagList;

}
