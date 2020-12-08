package com.fzufood.service.job;

import com.fzufood.WhatToEatApplication;
import com.fzufood.dto.DishRecommend;
import com.fzufood.dto.Recommend;
import com.fzufood.entity.Dish;
import com.fzufood.entity.Window;
import com.fzufood.repository.DishCommentMapper;
import com.fzufood.repository.DishTagMapper;
import com.fzufood.repository.WindowMapper;
import com.fzufood.util.PicturePath;
import com.fzufood.util.SpringUtil;
import com.fzufood.util.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author qizong007
 * @create 2020/12/8 19:54
 */
@Component
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class PopularJob implements Job {

    @Autowired
    private WindowMapper windowMapper;// = WhatToEatApplication.applicationContext.getBean(WindowMapper.class);
    @Autowired
    private DishTagMapper dishTagMapper;// = WhatToEatApplication.applicationContext.getBean(DishTagMapper.class);
    @Autowired
    private DishCommentMapper dishCommentMapper;// = WhatToEatApplication.applicationContext.getBean(DishCommentMapper.class);

    public static PopularJob popularJob;
    private Recommend recommend;
    private List<DishRecommend> dishRecommends;

    @PostConstruct
    public void init(){
        popularJob = this;
        popularJob.windowMapper = this.windowMapper;
        popularJob.dishCommentMapper = this.dishCommentMapper;
        popularJob.dishTagMapper = this.dishTagMapper;
        popularJob.recommend = new Recommend();
        popularJob.dishRecommends = new ArrayList<>();
    }

    public Recommend getRecommend() {
        return recommend;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Window> windowList = popularJob.windowMapper.listWindows();
        windowList.sort(new Comparator<Window>() {
            Integer tagnum1;
            BigDecimal i1;
            Integer tagnum2;
            BigDecimal i2;
            Double avgStarsByWindowId;

            @Override
            public int compare(Window window1, Window window2) {
                Integer window1Id = window1.getWindowId();
                tagnum1 = popularJob.dishTagMapper.countTagNumByWindowId(window1Id);
                avgStarsByWindowId = popularJob.dishCommentMapper.getAvgStarsByWindowId(window1.getWindowId());
                if (avgStarsByWindowId != null) {
                    i1 = new BigDecimal(avgStarsByWindowId * 0.6 + tagnum1 * 0.4);
                } else {
                    i1 = BigDecimal.ZERO;
                }
                Integer window2Id = window2.getWindowId();
                tagnum2 = popularJob.dishTagMapper.countTagNumByWindowId(window2Id);
                avgStarsByWindowId = popularJob.dishCommentMapper.getAvgStarsByWindowId(window2.getWindowId());
                if (avgStarsByWindowId != null) {
                    i2 = new BigDecimal(avgStarsByWindowId * 0.6 + tagnum2 * 0.4);
                } else {
                    i2 = BigDecimal.ZERO;
                }
                return i2.compareTo(i1);
            }
        });
        for (Window window : windowList) {
            DishRecommend dishRecommend = new DishRecommend();
            dishRecommend.setWindowId(window.getWindowId());
            dishRecommend.setWindowName(window.getWindowName());
            dishRecommend.setPngSrc(getWindowPngSrc(window.getWindowId()));
            if (!window.getDescription().equals("")) {
                dishRecommend.setDescription(window.getDescription());
            }
            dishRecommend.setStar(popularJob.dishCommentMapper.getAvgStarsByWindowId(window.getWindowId()));
            List<Dish> dishList = popularJob.windowMapper.listDishesById(window.getWindowId());
            dishRecommend.setDish(dishList);
            dishRecommend.setCanteenName(popularJob.windowMapper.getWindowById(window.getWindowId()).getCanteen().getCanteenName());
            if (dishList.size() > 3) {
                dishRecommend.setDish(dishList.subList(0, 3));
            } else {
                dishRecommend.setDish(dishList);
            }
            popularJob.dishRecommends.add(dishRecommend);
        }
        popularJob.recommend.setWindowList(popularJob.dishRecommends.subList(0, 15));
        System.out.println("热门推荐计算完毕");
    }

    /**
     * 获取窗口图片路径
     * @param windowId
     * @return String
     * @author qizong007
     * @date 21:31 2020/11/23
     **/
    public String getWindowPngSrc(Integer windowId) {
        Window window = popularJob.windowMapper.getWindowById(windowId);
        String windowName = window.getWindowName();
        Integer canteenId = window.getCanteen().getCanteenId();
        if (canteenId == 2 || canteenId == 8) {
            return PicturePath.PREFIX + canteenId + "/" + windowName + ".JPG";
        } else {
            return PicturePath.PREFIX + canteenId + "/" + windowName + ".jpg";
        }
    }

}
