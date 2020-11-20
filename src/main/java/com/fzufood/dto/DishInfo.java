package com.fzufood.dto;

import com.fzufood.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishInfo{

    private String dishName = "";
    private BigDecimal price = new BigDecimal(0);
    private Double userStar = 0.0;
    private Double star = 0.0;
    private Integer[] starNum = new Integer[5];
    private String windowName = "";
    private Integer windowId = -1;
    private List<TagInfo> tagList = new ArrayList<>();

}
