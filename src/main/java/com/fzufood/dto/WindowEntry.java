package com.fzufood.dto;

import com.fzufood.entity.Dish;
import com.fzufood.entity.Tag;
import com.fzufood.entity.Window;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * window列表接口的基本单位
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindowEntry{

    private Integer windowId = -1;
    private String windowName = "";
    private String pngSrc = "";
    private String description = "";
    private String mapSrc = "";
    private String canteenName = "";
    private Double star = 0.0;
    private List<Tag> tags = new ArrayList<>();
    private Boolean isMarked = false;
    private List<Dish> dishes = new ArrayList<>();
}
