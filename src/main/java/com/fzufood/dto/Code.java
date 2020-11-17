package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每一个返回的json对象都要有状态码
 * @Author qizong007
 * @create 2020/11/17 16:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Code {
    Integer code;
}
