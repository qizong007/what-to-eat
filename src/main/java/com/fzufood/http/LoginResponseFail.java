package com.fzufood.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qizong007
 * @create 2020/11/19 17:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseFail {
    private String unionid;
    private Integer errcode;
    private String errmsg;
}
