package com.fzufood.service.impl;

import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.WindowEntry;
import com.fzufood.service.WindowService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class WindowServiceImpl implements WindowService {
    @Override
    public DishRecommend dishRecommend(Integer type, Integer userId) {
        return null;
    }
    @Override
    public List<WindowEntry> list(Integer windowId, Integer userId) {
        List<Window> windowList = userMapper.listLikeWindowsById(userId);
        List<WindowEntry> windowEntries = new ArrayList<>();
        for(Window window : windowList){
            WindowEntry windowEntry = new WindowEntry();
            windowEntry.setWindow(window.getWindow());
            windowEntry.setCanteenName(window.getCanteenName());
            windowEntry.setTag(window.getTag());
            windowEntry.setStar(countStarsOnWindow(window.getWindowId()));
            windowEntry.setIsMarked(window.isMarked);
            windowEntries.add(windowEntry);
        }
        return windowEntries;
    }
    /**
     * 根据dishId，查出该道菜品星级
     */
    private Double countStarsOnWindow(Integer windowId){
        List<WindowComment> windowComments = windowCommentMapper.listWindowCommentsByWindowId(windowId);
        Double stars = 0.0;
        for(WindowComment windwoComment : windowComments){
            stars += windowComment.getStars();
        }
        return stars/windowComments.size();
    }
    @Override
    public List<WindowEntry> getMarkedWindow(Integer userId) {

    }
    @Override
    public Integer updateMarkedWindow( Integer userId, Integer windowId)  {
        return null;
    }
}
