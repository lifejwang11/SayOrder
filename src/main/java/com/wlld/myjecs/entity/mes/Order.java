package com.wlld.myjecs.entity.mes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@ApiModel
@Getter
@Setter
public class Order {
    @ApiModelProperty(value = "关键词集合")
    private Set<String> keyWords;//关键词
    @ApiModelProperty(value = "关键词类别id", example = "2")
    private int keyword_type_id;//关键词类别
}
