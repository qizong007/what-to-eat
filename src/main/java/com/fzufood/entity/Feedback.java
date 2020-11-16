package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("反馈")
public class Feedback {

    @ApiModelProperty("反馈ID")
    private Integer feedbackId;

    @ApiModelProperty("反馈时间")
    private Timestamp submitTime;

    @ApiModelProperty("反馈内容")
    private String content;

    @ApiModelProperty("所属的用户")
    private User user;
}
