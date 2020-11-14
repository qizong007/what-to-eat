package com.fzufood.controller;

import com.fzufood.dto.SystemInfo;
import com.fzufood.service.SystemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 获取系统基础数据接口
     * @return
     */
    @ApiOperation("获取系统基础数据接口")
    @GetMapping("/getInfo")
    public SystemInfo getInfo(){
        return systemService.getInfo();
    }

}
