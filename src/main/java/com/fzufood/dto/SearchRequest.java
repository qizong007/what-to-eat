package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author qizong007
 * @create 2020/11/26 21:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String searchName;
    private List<Integer> tagList;
    private Integer canteenId;
}
