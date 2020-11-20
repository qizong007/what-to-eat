package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagInfo {
    private Integer tagId = -1;
    private Integer tagNum = 0;
    private Boolean hasTagged = false;
    private String tagName = "";
}
