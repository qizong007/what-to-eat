package com.fzufood.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qizong007
 * @create 2020/11/26 16:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crowded {
    private String canteenName = "";
    private Integer fullNum = 0;
    private Integer peoNum = 0;
    private String status = "未知";
    private Integer canteenId = -1;
}
