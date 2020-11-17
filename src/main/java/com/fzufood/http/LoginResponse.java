package com.fzufood.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信小程序返回的响应
 * @Author qizong007
 * @create 2020/11/17 19:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String openid;
    private String session_key;
    private String unionid;
    private Integer errcode;
    private String errmsg;

}
