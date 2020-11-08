package com.fzufood.dto;

import com.fzufood.entity.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRecommend {

    private Integer windowId;
    private String windowName;
    private File pngSrc;
    private String description;
    private String canteenName;
    private Double star;
    private List<Dish> dish;

}
