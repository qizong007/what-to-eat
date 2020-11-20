package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author qizong007
 * @create 2020/11/19 20:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private List<DishRecommend> searchList = new ArrayList<>();
}
