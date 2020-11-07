package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("食堂")
public class Canteen {

    @ApiModelProperty("食堂ID")
    private Integer canteenId;

    @ApiModelProperty("食堂名字")
    private String canteenName;

    @ApiModelProperty("食堂窗口")
    //private List<Window> windows;
    private Integer windowId;

}
