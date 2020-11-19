package com.fzufood.http;

import com.fzufood.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author qizong007
 * @create 2020/11/19 23:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInfoResponse {
    private Integer userId;
    private List<Tag> preferredList;
    private List<Tag> avoidList;
}
