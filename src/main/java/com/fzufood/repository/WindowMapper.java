package com.fzufood.repository;
import com.fzufood.entity.Dish;
import com.fzufood.entity.Window;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * @author buzhouI
 */
@Repository
@Mapper
public interface WindowMapper {

    /**
     * 查询所有window(windowId, windowName, location, profile, description, canteen)
     * @return List<Window>
     */
    List<Window> listWindows();

    /**
     * 根据windowId查询对应的window(windowId, windowName, location, profile, description, canteen)
     * @param windowId
     * @return Window
     */
    Window getWindowById(Integer windowId);

    /**
     * 根据windowId查询该window拥有的所有dish(dishId, dishName, price, byWeight)
     * @param windowId
     * @return List<Dish>
     */
    List<Dish> listDishesById(Integer windowId);

    /**
     * 插入新的window(windowId, windowName, location, profile, description, canteen)
     * @param window
     * @return int
     */
    int saveWindow(Window window);

    /**
     * 根据windowId更新window(windowId, windowName, location, profile, description, canteen)
     * @param window
     * @return int
     */
    int updateWindow(Window window);

    /**
     * 根据windowId删除window(windowId, windowName, location, profile, description, canteen)
     * @param windowId
     * @return int
     */
    int removeWindowById(Integer windowId);
}