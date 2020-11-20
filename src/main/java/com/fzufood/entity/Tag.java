package com.fzufood.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Integer tagId = -1;

    @ApiModelProperty("标签内容")
    @JSONField(name = "tagName")
    @JsonProperty("tagName")
    private String content = "";
}
