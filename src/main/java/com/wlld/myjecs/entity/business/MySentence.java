package com.wlld.myjecs.entity.business;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MySentence {
    private String word;//语句内容
    private int type_id;//类别id
    private List<MyKeywordStudy> myKeywordStudyList = new ArrayList<>();
}
