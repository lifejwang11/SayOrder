package com.wlld.myjecs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeywordSql {
    @TableId(type = IdType.AUTO)
    private Integer id;//该关键词id
    private Integer sentence_id;//该关键词对应的语句id
    private String keyword;//关键词内容
    private Integer keyword_type_id;//该关键词类型id
    @TableField(exist = false)
    private Integer type_id;//该关键词类型id
    @TableField(exist = false)
    private String keywordTypeIds;//该关键词类型id
}
