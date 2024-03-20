package com.wlld.myjecs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class KeywordType {
    @ApiModelProperty(value = "该关键词类型id", example = "1")
    @TableId(type = IdType.AUTO)
    private int keyword_type_id;//该关键词类型id
    @ApiModelProperty(value = "该关键词类型对应得语句类别", example = "1")
    private int type_id;//该关键词类型对应得语句类别
    @ApiModelProperty(value = "该关键词对应的订单信息", example = "订单产品名称")
    private String keyword_mes;//该关键词对应的订单信息
    @ApiModelProperty(value = "用户语句缺失该关键词对应的回复问题", example = "您想吃点什么呢？")
    private String answer;//用户语句缺失该关键词对应的回复问题\
    @ApiModelProperty(value = "该种类关键词被标注的数量", example = "2")
    private int type_number;
}
