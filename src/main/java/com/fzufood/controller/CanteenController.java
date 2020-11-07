package com.fzufood.controller;

import com.fzufood.entity.Canteen;
import com.fzufood.repository.CanteenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/canteen")
public class CanteenController {

    @Autowired
    private CanteenMapper canteenMapper;

    @GetMapping("/findAll")
    public List<Canteen> findAll(){
        System.out.println("findAll");
        return canteenMapper.findAll();
    }

    @GetMapping("/findById")
    public Canteen findById(Integer id){
        System.out.println("findById");
        return canteenMapper.findById(id);
    }
}
