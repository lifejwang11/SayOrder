package com.wlld.myjecs.entity.mes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class AdminSentence {
    @ApiModelProperty(value = "语句id",example = "1")
    private int sentence_id;
    @ApiModelProperty(value = "语句",example = "我的小狗生病了")
    private String word;
    @ApiModelProperty(value = "所属语句分类",example = "1")
    private int type_id;
}
