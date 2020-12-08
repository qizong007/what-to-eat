package com.fzufood.service.impl;

import com.fzufood.dto.JsonObject;
import com.fzufood.dto.SystemInfo;
import com.fzufood.dto.UpdateDishTag;
import com.fzufood.entity.Canteen;
import com.fzufood.entity.Tag;
import com.fzufood.repository.CanteenMapper;
import com.fzufood.repository.DishTagMapper;
import com.fzufood.repository.TagMapper;
import com.fzufood.service.SystemService;
import com.fzufood.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService{

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CanteenMapper canteenMapper;
    @Autowired
    private DishTagMapper dishTagMapper;

    /**
     * 获取系统基础数据接口
     * @author qizong007
     * @return SystemInfo
     */
    @Override
    public JsonObject<SystemInfo> getInfo() {
        JsonObject<SystemInfo> jsonObject = new JsonObject<>();
        SystemInfo systemInfo = new SystemInfo();
        List<Tag> allTags = tagMapper.listTags();
        if(allTags == null){
            allTags = new ArrayList<>();
        }
        List<UpdateDishTag> tags = new ArrayList<>();
        for(Tag tag : allTags){
            int tagNum = dishTagMapper.listDishTagsByTagId(tag.getTagId()).size();
            tags.add(new UpdateDishTag(tag.getContent(),tag.getTagId(),tagNum,false));
        }
        List<Canteen> allCanteen = canteenMapper.listCanteens();
        if(allCanteen == null){
            allCanteen = new ArrayList<>();
        }
        systemInfo.setTags(tags);
        systemInfo.setCanteens(allCanteen);
        jsonObject.setCode(StatusCode.SUCCESS);
        jsonObject.setData(systemInfo);
        return jsonObject;
    }
    
}
