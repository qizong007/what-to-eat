package com.fzufood.service.impl;

import com.fzufood.dto.*;
import com.fzufood.entity.Dish;
import com.fzufood.entity.DishComment;
import com.fzufood.entity.User;
import com.fzufood.entity.Window;
import com.fzufood.repository.DishCommentMapper;
import com.fzufood.repository.UserMapper;
import com.fzufood.repository.WindowMapper;
import com.fzufood.service.WindowService;
import com.fzufood.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public JsonObject<List<DishRecommend>> recommend(Integer type, Integer userId) {
        return null;
    }

    @Override
    public JsonObject<WindowEntry> info(Integer windowId, Integer userId) {
        return null;
    }

    /**
     * 获取收藏窗口
     * @author gaoyichao33
     * @param userId
     * @return JsonObject<List<DishRecommend>>
     */
    @Override
    public JsonObject<List<DishRecommend>> getMarkedWindow(Integer userId) {
        JsonObject<List<DishRecommend>> jsonObject = new JsonObject<>();
        Code code = new Code();
        if(userId == null){
            code.setCode(StatusCode.MISSING_PARAMETERS);
            jsonObject.setCode(code);
            jsonObject.setData(null);
            return jsonObject;
        }
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
        jsonObject.setCode(new Code(StatusCode.SUCCESS));
        jsonObject.setData(dishRecommends);
        return jsonObject;
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
    public Code updateMarkedWindow(Integer userId, Integer windowId)  {
        User user = userMapper.getUserById(userId);
        List<Window> windowList = userMapper.listMarkWindowsById(userId);
        Window  window = windowMapper.getWindowById(windowId);
        windowList.add(window);
        user.setMarkWindows(windowList);
        if(windowMapper.updateWindow(window) != 0){
            return new Code(StatusCode.SUCCESS);
        }else {
            return new Code(StatusCode.FAIL_TO_UPDATE_MARKED_WINDOW);
    }

}
}
