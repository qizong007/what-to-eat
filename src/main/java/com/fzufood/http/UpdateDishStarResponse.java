package com.fzufood.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qizong007
 * @create 2020/11/19 23:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDishStarResponse {
    private Integer userId;
    private Integer dishId;
    private Double star;
}
