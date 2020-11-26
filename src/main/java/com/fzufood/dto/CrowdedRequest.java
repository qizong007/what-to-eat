package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qizong007
 * @create 2020/11/26 16:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrowdedRequest {
    private String roomName;
    private Integer fullNum;
    private Integer peoNum;
    private String status;
    private Integer em;
    private Integer closetime;
    private Integer incount;
    private Integer outcount;
}
