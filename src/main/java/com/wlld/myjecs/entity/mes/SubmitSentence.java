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
public class SubmitSentence {
    @ApiModelProperty(value = "语句内容", example = "我想吃麻辣烫")
    @NotNull
    private String word;//语句内容
    @ApiModelProperty(value = "语义id", example = "1")
    @NotNull
    private int type_id;//类别id
    @ApiModelProperty(value = "关键词集合")
    private List<UpKeyword> upKeywordList;
}
