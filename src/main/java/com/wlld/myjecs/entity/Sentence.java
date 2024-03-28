package com.wlld.myjecs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Sentence {
    @TableId(type = IdType.AUTO)
    private Integer sentence_id;//语句id
    private String word;//语句内容
    private Integer type_id;//类别id
    private Integer adminID;//标注人的id
    private String date;//标注日期
    @TableField(exist = false)
    private List<KeywordSql> sqls;
}
