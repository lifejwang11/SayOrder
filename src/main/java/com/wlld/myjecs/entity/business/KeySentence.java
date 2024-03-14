package com.wlld.myjecs.entity.business;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeySentence {
    private String word;//语句内容
    private int keyword_type_id;//类别id
    private String keyword;//关键词
}
