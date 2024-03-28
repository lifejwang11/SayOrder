package com.wlld.myjecs.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author fn
 * @description
 * @date 2024/3/19 16:59
 */
@Data
public class TreeVo {
    Integer id;
    String name;
    List<TreeVo> children;
}
