package com.wlld.myjecs.entity.business;

import lombok.Getter;
import lombok.Setter;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
@Getter
@Setter
public class KeyWord {
    private String keyword;
    private int type;
    private long index;//该关键词索引id
}
