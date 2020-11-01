package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("标签")
public class Tag {

    @ApiModelProperty("标签ID")
    private Long tagId;

    @ApiModelProperty("标签内容")
    private String content;
}
