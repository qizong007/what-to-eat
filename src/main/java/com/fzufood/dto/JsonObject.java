package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qizong007
 * @create 2020/11/17 21:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonObject<T> {

    Code code;
    T data;

}
