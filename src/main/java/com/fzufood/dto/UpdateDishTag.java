package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishTag{

    private String tagName;
    private Integer tagId;
    private Integer tagNum;
    private Boolean hasTagged;

}
