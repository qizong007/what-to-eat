package com.fzufood.controller;

import com.fzufood.entity.Admin;
import com.fzufood.entity.DishTag;
import com.fzufood.entity.Tag;
import com.fzufood.entity.Window;
import com.fzufood.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author qizong007
 * @create 2020/11/22 21:28
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private CanteenMapper canteenMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WindowMapper windowMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private DishTagMapper dishTagMapper;

    @RequestMapping("/test")
    public String test(Model model){
        String str = "这只是个测试hhh";
        model.addAttribute("str",str);
        return "test";
    }

    /**
     * 登录请求，返回表单对象
     * @param model
     * @return String
     */
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("admin",new Admin());
        return "login";
    }

    /**
     * 接收表单，返回结果
     * @param admin
     * @return String
     */
    @PostMapping("/login")
    public String login(Admin admin, Model model, HttpServletRequest request){
        String adminName = admin.getAdminName();
        String password = admin.getAdminPassword();
        if(adminMapper.getAdminByNameAndPassword(adminName,password) != null){
            HttpSession session = request.getSession();
            session.setAttribute("admin",admin);
            model.addAttribute("admin",admin);
            return "success";
        }else{
            return "login";
        }
    }

    @GetMapping("/canteen")
    public String canteen(Model model){
        model.addAttribute("canteens",canteenMapper.listCanteens());
        return "canteen";
    }

    @GetMapping("/user")
    public String user(Model model){
        model.addAttribute("users",userMapper.listUsers());
        return "user";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(Model model, Integer userId){
        userMapper.removeUser(userId);
        model.addAttribute("users",userMapper.listUsers());
        return "user";
    }

    @GetMapping("/window")
    public String window(Model model, Integer canteenId){
        model.addAttribute("canteen",canteenMapper.getCanteenById(canteenId));
        model.addAttribute("windows",canteenMapper.listWindowsById(canteenId));
        return "window";
    }

    @GetMapping("/deleteWindow")
    public String deleteWindow(Model model, Integer canteenId,Integer windowId){
        windowMapper.removeWindowById(windowId);
        model.addAttribute("canteen",canteenMapper.getCanteenById(canteenId));
        model.addAttribute("windows",canteenMapper.listWindowsById(canteenId));
        return "window";
    }

    @GetMapping("/tag")
    public String tag(Model model){
        model.addAttribute("tags",tagMapper.listTags());
        return "tag";
    }

    @PostMapping("/addTag")
    public String addTag(Tag tag,Model model){
        tag.setTagId(null);
        tagMapper.saveTag(tag);
        model.addAttribute("tags",tagMapper.listTags());
        return "tag";
    }

    @GetMapping("/addTag")
    public String addTag(Model model){
        model.addAttribute("tag",new Tag());
        return "addTag";
    }

    @GetMapping("/deleteTag")
    public String deleteTag(Model model, Integer tagId){
        tagMapper.removeTagById(tagId);
        model.addAttribute("tags",tagMapper.listTags());
        return "tag";
    }

    @GetMapping("/feedback")
    public String feedback(Model model){
        model.addAttribute("feedbackList",feedbackMapper.listFeedback());
        return "feedback";
    }

    @GetMapping("/deleteFeedback")
    public String deleteFeedback(Model model, Integer feedbackId){
        feedbackMapper.removeFeedbackById(feedbackId);
        model.addAttribute("feedbackList",feedbackMapper.listFeedback());
        return "feedback";
    }

    @GetMapping("/dish")
    public String dish(Model model, Integer windowId){
        model.addAttribute("window",windowMapper.getWindowById(windowId));
        model.addAttribute("dishes",windowMapper.listDishesById(windowId));
        return "dish";
    }

    @GetMapping("/dishTag")
    public String dishTag(Model model,Integer dishId){
        model.addAttribute("dish",dishMapper.getDishById(dishId));
        model.addAttribute("tags",dishMapper.listTagsById(dishId));
        return "dishTag";
    }

    @PostMapping("/addDishTag")
    public String addDishTag(Tag tag,Model model,HttpServletRequest request){
        Integer dishId = (Integer)request.getSession().getAttribute("dishId");
        Tag desTag = tagMapper.getTagByContent(tag.getContent());
        if(desTag == null){
            tag.setTagId(null);
            tagMapper.saveTag(tag);
            desTag = tagMapper.getTagByContent(tag.getContent());
        }
        dishTagMapper.saveDishTag(new DishTag(1,dishId,desTag.getTagId()));
        model.addAttribute("dish",dishMapper.getDishById(dishId));
        model.addAttribute("tags",dishMapper.listTagsById(dishId));
        return "dishTag";
    }

    @GetMapping("/addDishTag")
    public String addDishTag(Model model,Integer dishId,HttpServletRequest request){
        model.addAttribute("tag",new Tag());
        model.addAttribute("dish",dishMapper.getDishById(dishId));
        request.getSession().setAttribute("dishId",dishId);
        return "addDishTag";
    }
}
