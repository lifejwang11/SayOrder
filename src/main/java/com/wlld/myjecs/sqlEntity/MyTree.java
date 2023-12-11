package com.wlld.myjecs.sqlEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class MyTree {
    @ApiModelProperty(value = "类别id", example = "2")
    private int type_id;//类别id
    @ApiModelProperty(value = "类别名称", example = "餐饮")
    private String title;//名称
    @ApiModelProperty(value = "样本条目数", example = "0")
    private int sentence_nub;
}
