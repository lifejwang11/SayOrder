package com.wlld.myjecs.entity.mes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class UpKeyword {
    @ApiModelProperty(value = "关键词", example = "麻辣烫")
    @NotNull
    private String keyword;//关键词
    @ApiModelProperty(value = "关键词id", example = "1")
    @NotNull
    private int keyword_type_id;//该关键词类型id
}
