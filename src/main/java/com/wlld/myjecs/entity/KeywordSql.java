package com.wlld.myjecs.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeywordSql {
    private int id;//该关键词id
    private int sentence_id;//该关键词对应的语句id
    private String keyword;//关键词内容
    private int keyword_type_id;//该关键词类型id
}
