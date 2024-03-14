package com.wlld.myjecs.entity.mes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
@Getter
@Setter
@ApiModel
public class Shop {
    @ApiModelProperty(value = "语义分类", example = "2")
    private int type_id;
    @ApiModelProperty(value = "用户是否需要打折", example = "true")
    private boolean isFree;
    @ApiModelProperty(value = "索引，0是无索引，一般用于店铺主键", example = "1")
    private long index;
    @ApiModelProperty(value = "订单集合")
    private List<Order> orders;
    @ApiModelProperty(value = "核心关键词")
    private String keyword;
    @ApiModelProperty(value = "回答反问")
    private String answer;
}
