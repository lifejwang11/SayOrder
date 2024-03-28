package com.wlld.myjecs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
@TableName("my_tree")
public class MyTree {
    @ApiModelProperty(value = "类别id", example = "2")
    @TableId(type = IdType.AUTO)
    private int type_id;//类别id
    @ApiModelProperty(value = "类别名称", example = "餐饮")
    private String title;//名称
    @ApiModelProperty(value = "样本条目数", example = "0")
    private int sentence_nub;

    @TableField(exist = false)
    private List<KeywordType> types;

}
