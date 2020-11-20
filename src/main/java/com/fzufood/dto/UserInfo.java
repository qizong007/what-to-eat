package com.fzufood.dto;

import com.fzufood.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo{

    private List<Tag> preferredList = new ArrayList<>();
    private List<Tag> avoidList = new ArrayList<>();
    private List<Tag> allList = new ArrayList<>();

}
