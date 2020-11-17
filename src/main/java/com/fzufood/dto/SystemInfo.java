package com.fzufood.dto;

import com.fzufood.entity.Canteen;
import com.fzufood.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfo{

    private List<Tag> tags;
    private List<Canteen> canteens;

}
