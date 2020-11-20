package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("反馈")
public class Feedback {

    @ApiModelProperty("反馈ID")
    private Integer feedbackId = -1;

    @ApiModelProperty("反馈时间")
    private Timestamp submitTime = new Timestamp(new Date().getTime());

    @ApiModelProperty("反馈内容")
    private String content = "";

    @ApiModelProperty("所属的用户")
    private User user = new User();
}
