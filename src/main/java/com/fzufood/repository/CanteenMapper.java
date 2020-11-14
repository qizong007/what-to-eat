package com.fzufood.repository;

import com.fzufood.entity.Canteen;
import com.fzufood.entity.Window;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author buzhouI
 */
@Repository
@Mapper
public interface CanteenMapper {

    /**
     * 查询所有canteen(canteenId, canteenName)
     * @return List<Canteen>
     */
    List<Canteen> listCanteens();

    /**
     * 根据canteenId查询对应的canteen(canteenId, canteenName)
     * @param canteenId
     * @return Canteen
     */
    Canteen getCanteenById(Integer canteenId);

    /**
     * 根据canteenId查询该canteen拥有的所有window(windowId, windowName, location, profile, description)
     * @param canteenId
     * @return List<Window>
     */
    List<Window> listWindowsById(Integer canteenId);

    /**
     * 插入新的canteen(canteenId, canteenName)
     * @param canteen
     * @return int
     */
    int saveCanteen(Canteen canteen);

    /**
     *根据canteenId更新canteen(canteenId, canteenName)
     * @param canteen
     * @return int
     */
    int updateCanteen(Canteen canteen);

    /**
     * 根据canteenId删除canteen(canteenId, canteenName)
     * @param canteenId
     * @return int
     */
    int removeCanteenById(Integer canteenId);
}
