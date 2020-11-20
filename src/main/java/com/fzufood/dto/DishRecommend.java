package com.fzufood.dto;

import com.fzufood.entity.Dish;
import com.fzufood.util.PicturePath;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRecommend{

    private Integer windowId = -1;
    private String windowName = "";
    private String pngSrc = PicturePath.DEFAULT;
    private String description = "暂无介绍";
    private String canteenName = "";
    private Double star = 0.0;
    private List<Dish> dish = new ArrayList<>();

}
