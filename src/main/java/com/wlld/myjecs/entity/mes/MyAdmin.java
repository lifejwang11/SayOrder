package com.wlld.myjecs.entity.mes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@ApiModel
public class MyAdmin {
    @ApiModelProperty(value = "账号", example = "thenk")
    @NonNull
    private String account;//账号
    @ApiModelProperty(value = "密码", example = "123456")
    @NonNull
    private String pass_word;//密码
    @ApiModelProperty(value = "名称", example = "用户姓名")
    private String name;//账户拥有者姓名
}
