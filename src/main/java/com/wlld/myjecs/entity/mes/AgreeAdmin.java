package com.wlld.myjecs.entity.mes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class AgreeAdmin {
    @ApiModelProperty(value = "需要处理的标注员id", example = "12")
    private int id;
    @ApiModelProperty(value = "是否同意通过", example = "true")
    private boolean agree;
}
