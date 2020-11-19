package com.fzufood.dto;

import com.fzufood.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo{

    private List<Tag> preferredList;
    private List<Tag> avoidList;
    private List<Tag> allList;

}
