package com.fzufood.dto;

import com.fzufood.entity.Tag;
import com.fzufood.entity.Window;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * window列表接口的基本单位
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindowEntry {

    private Window window;
    private List<Tag> tags;
    private String canteenName;
    private Double stars;
    private Boolean isMarked;

}
