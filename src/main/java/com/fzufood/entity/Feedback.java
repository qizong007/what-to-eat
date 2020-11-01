package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("反馈")
public class Feedback {

    @ApiModelProperty("反馈ID")
    private Long feedbackId;

    @ApiModelProperty("反馈时间")
    private Date submitTime;

    @ApiModelProperty("反馈内容")
    private String content;

    @ApiModelProperty("菜品ID")
    private Long dishId;

    @ApiModelProperty("窗口ID")
    private Long windowId;

}
