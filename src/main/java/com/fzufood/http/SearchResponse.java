package com.fzufood.http;

import com.fzufood.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author qizong007
 * @create 2020/11/23 22:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    private String searchName;
    private List<Tag> tagList;
    private Integer canteenId;
}
