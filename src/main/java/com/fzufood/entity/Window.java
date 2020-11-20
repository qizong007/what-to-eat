package com.fzufood.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("窗口实体类")
public class Window {

    @ApiModelProperty("窗口ID")
    private Integer windowId = -1;

    @ApiModelProperty("窗口名")
    private String windowName = "";

    @ApiModelProperty("窗口位置")
    private String locationURI = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%9A%82%E6%97%A0%E5%9B%BE%E7%89%87&step_word=&hs=2&pn=3&spn=0&di=99220&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=34663700%2C1472516892&os=3049312912%2C659538844&simid=4220463564%2C548586239&adpicid=0&lpn=0&ln=890&fr=&fmq=1605867459817_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F01%2F51%2F05%2F455745757e09bb3.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Flafij3t_z%26e3Bv54AzdH3Ff7vwtAzdH3F89lll0bl_z%26e3Bip4s&gsm=4&rpstart=0&rpnum=0&islist=&querylist=&force=undefined";

    @ApiModelProperty("店面图片")
    private String profileURI = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%9A%82%E6%97%A0%E5%9B%BE%E7%89%87&step_word=&hs=2&pn=3&spn=0&di=99220&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=34663700%2C1472516892&os=3049312912%2C659538844&simid=4220463564%2C548586239&adpicid=0&lpn=0&ln=890&fr=&fmq=1605867459817_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F01%2F51%2F05%2F455745757e09bb3.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Flafij3t_z%26e3Bv54AzdH3Ff7vwtAzdH3F89lll0bl_z%26e3Bip4s&gsm=4&rpstart=0&rpnum=0&islist=&querylist=&force=undefined";

    @ApiModelProperty("窗口介绍")
    private String description = "暂无介绍";

    @ApiModelProperty("所属的餐厅")
    private Canteen canteen = new Canteen();

    @ApiModelProperty("菜品")
    private List<Dish> dishes = new ArrayList<>();
}
