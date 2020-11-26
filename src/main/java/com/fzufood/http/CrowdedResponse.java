package com.fzufood.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author qizong007
 * @create 2020/11/26 16:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrowdedResponse {
    private Integer code = -1;
    private List<Crowded> canteenList = new ArrayList<>();
}
