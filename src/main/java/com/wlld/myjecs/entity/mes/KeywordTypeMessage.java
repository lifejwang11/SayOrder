package com.wlld.myjecs.entity.mes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class KeywordTypeMessage {
    @ApiModelProperty(value = "关键词描述",example = "餐饮食品")
    private String keyword_mes;//关键词描述
    @ApiModelProperty(value = "缺失该关键词回复问题",example = "您具体想吃什么？若没有我们为您推荐《幸福快餐》餐厅，您看是否满意？")
    private String answer;//缺失该关键词回复问题
}
