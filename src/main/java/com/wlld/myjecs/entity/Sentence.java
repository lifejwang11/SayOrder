package com.wlld.myjecs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Sentence {
    @TableId
    private int sentence_id;//语句id
    private String word;//语句内容
    private int type_id;//类别id
    private int adminID;//标注人的id
    private String date;//标注日期
    @TableField(exist = false)
    private List<KeywordSql> sqls;
}
