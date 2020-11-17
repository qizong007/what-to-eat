package com.fzufood.service.impl;

import com.fzufood.dto.DishEntry;
import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.WindowEntry;
import com.fzufood.entity.*;
import com.fzufood.repository.*;
import com.fzufood.service.WindowService;
import com.fzufood.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class WindowServiceImpl implements WindowService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WindowMapper windowMapper;
    @Autowired
    private DishCommentMapper dishCommentMapper;
    @Autowired
    private DishMapper dishMapper;
    @Override
    public List<DishRecommend> recommend(Integer type, Integer userId) {

        return null;
    }
    @Override
    public WindowEntry info(Integer windowId, Integer userId) {
        WindowEntry windowEntry = new WindowEntry();
        List<Dish> dishList =  windowMapper.listDishesById(windowId);

        windowEntry.setWindowId(windowMapper.getWindowById(windowId).getWindowId());
        windowEntry.setWindowName(windowMapper.getWindowById(windowId).getWindowName());
        windowEntry.setPngSrc(windowMapper.getWindowById(windowId).getProfileURI());
        windowEntry.setDescription(windowMapper.getWindowById(windowId).getDescription());
        windowEntry.setMapSrc(windowMapper.getWindowById(windowId).getLocationURI());
        windowEntry.setCanteenName(windowMapper.getWindowById(windowId).getCanteen().getCanteenName());
        windowEntry.setStar(countStarsOnWindow(windowId));
//        for (Dish dish : dishList){
//            List<Tag> tags = dishMapper.listTagsById(dish.getDishId());
//          TODO: 窗口的List<Tag>
//        }
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        windowEntry.setIsMarked(windowList.contains(windowId));
        windowEntry.setDishes(dishList);
        return windowEntry;
    }
    /**
     * 获取收藏窗口
     * @author gaoyichao33
     * @param userId
     * @return List<DishRecommend>
     */
    @Override
    public List<DishRecommend> getMarkedWindow(Integer userId) {
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        List<DishRecommend> dishRecommends = new ArrayList<>();
        for(Window window : windowList){
            DishRecommend dishRecommend = new DishRecommend();
            dishRecommend.setWindowId(window.getWindowId());
            dishRecommend.setWindowName(window.getWindowName());
            dishRecommend.setPngSrc(window.getProfileURI());
            dishRecommend.setDescription(window.getDescription());
            dishRecommend.setStar(countStarsOnWindow(window.getWindowId()));
            dishRecommend.setDish(window.getDishes());
            dishRecommends.add(dishRecommend);
        }
        return  dishRecommends;
    }
    /**
     * 根据windowId，查出该窗口星级
     * @author gaoyichao33
     * @param windowId
     * @return Double
     */
    private Double countStarsOnWindow(Integer windowId){
        List<Dish> dishList = windowMapper.listDishesById(windowId);
        Double stars = 0.0;
        for(Dish dish : dishList){
            stars += countStarsOnDish(dish.getDishId());
        }
        return stars/dishList.size();
    }
    private Double countStarsOnDish(Integer dishId){
        List<DishComment> dishComments = dishCommentMapper.listDishCommentsByDishId(dishId);
        Double stars = 0.0;
        for(DishComment dishComment : dishComments){
            stars += dishComment.getStars();
        }
        return stars/dishComments.size();
    }
    /**
     * 更新收藏窗口
     * @author gaoyichao33
     * @param userId,windowId
     * @return Integer
     */
    @Override
    public Integer updateMarkedWindow( Integer userId, Integer windowId)  {
        User user = userMapper.getUserById(userId);
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        Window  window = windowMapper.getWindowById(windowId);
        windowList.add(window);
        user.setMarkWindows(windowList);
        if(windowMapper.updateWindow(window) != 0){
            return StatusCode.SUCCESS;
        }else {
            return StatusCode.FAIL_TO_UPDATE_MARKEDWINDOW;
    }

}
}
