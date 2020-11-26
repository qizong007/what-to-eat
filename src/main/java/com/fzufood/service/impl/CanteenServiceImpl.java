package com.fzufood.service.impl;

import com.alibaba.fastjson.JSON;
import com.fzufood.dto.CrowdedRequest;
import com.fzufood.dto.JsonObject;
import com.fzufood.entity.Canteen;
import com.fzufood.http.Crowded;
import com.fzufood.http.CrowdedResponse;
import com.fzufood.http.HttpRequest;
import com.fzufood.repository.CanteenMapper;
import com.fzufood.service.CanteenService;
import com.fzufood.util.RequestPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author qizong007
 * @create 2020/11/26 17:05
 */
@Service
public class CanteenServiceImpl implements CanteenService {

    @Autowired
    private CanteenMapper canteenMapper;

    /**
     * 食堂拥挤程度接口
     * @author qizong007
     * @date 17:08 2020/11/26
     * @param
     * @return
     **/
    @Override
    public JsonObject<CrowdedResponse> crowded() {
        JsonObject<CrowdedResponse> jsonObject = new JsonObject<>();
        CrowdedResponse crowdedResponse = new CrowdedResponse();
        String crowdedStr = HttpRequest.sendGet(RequestPath.CROWDED,null);
        List<CrowdedRequest> crowdedRequestList = JSON.parseArray(crowdedStr,CrowdedRequest.class);
        if(crowdedRequestList == null){
            jsonObject.setCode(-1);
            crowdedResponse.setCanteenList(new ArrayList<>());
            jsonObject.setData(crowdedResponse);
        }
        List<Crowded> canteenList = new ArrayList<>();
        jsonObject.setCode(0);
        for(CrowdedRequest request : crowdedRequestList){
            Crowded crowded = new Crowded();
            crowded.setFullNum(request.getFullNum());
            crowded.setPeoNum(request.getPeoNum());
            crowded.setStatus(request.getStatus());
            Canteen canteen = getCanteenByRoomName(request.getRoomName());
            if(canteen.getCanteenId()==-1){
                continue;
            }
            crowded.setCanteenName(canteen.getCanteenName());
            crowded.setCanteenId(canteen.getCanteenId());
            canteenList.add(crowded);
        }
        Crowded crowded = new Crowded();
        crowded.setCanteenId(9);
        crowded.setCanteenName("教职工食堂");
        canteenList.add(crowded);
        crowdedResponse.setCanteenList(canteenList);
        jsonObject.setData(crowdedResponse);
        return jsonObject;
    }

    private Canteen getCanteenByRoomName(String room){
        if(room.equals("朝阳")){
            return canteenMapper.getCanteenById(5);
        }
        else if(room.equals("丁香园1楼")){
            return canteenMapper.getCanteenById(7);
        }
        else if(room.equals("丁香园2楼")){
            return canteenMapper.getCanteenById(8);
        }
        else if(room.equals("玫瑰园")){
            return canteenMapper.getCanteenById(1);
        }
        else if(room.equals("禾丰元")){
            return canteenMapper.getCanteenById(2);
        }
        else if(room.equals("京元")){
            return canteenMapper.getCanteenById(6);
        }
        else if(room.equals("紫荆园1楼")){
            return canteenMapper.getCanteenById(3);
        }
        else if(room.equals("紫荆园2楼")){
            return canteenMapper.getCanteenById(4);
        }
        return new Canteen();
    }
}
