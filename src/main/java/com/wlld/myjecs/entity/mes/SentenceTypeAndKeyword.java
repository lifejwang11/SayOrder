package com.wlld.myjecs.entity.mes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
@ApiModel
public class SentenceTypeAndKeyword {
    @ApiModelProperty(value = "该分类的id",example = "1")
    private int type_id;
    @ApiModelProperty(value = "该分类的类别名称",example = "餐饮美食")
    @NotNull
    private String title;//类别名称
    @ApiModelProperty(value = "该分类要添加的订单捕捉关键词")
    private List<KeywordTypeMessage> keywordTypeMessages;
}