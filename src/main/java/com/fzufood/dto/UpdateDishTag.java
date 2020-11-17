package com.fzufood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishTag{

    private String dishName;
    private Integer tagId;
    private Integer count;
    private Boolean markedTag;

}
