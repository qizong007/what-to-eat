package com.fzufood.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qizong007
 * @create 2020/11/19 23:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private Integer userId;
    private String content;
}
