package com.wlld.myjecs.entity.qo;

import lombok.Builder;
import lombok.Data;

/**
 * @author fn
 * @description
 * @date 2024/3/18 16:04
 */
@Data
@Builder
public class TreeQuery {
    private String title;
    private String keyword_mes;
}
