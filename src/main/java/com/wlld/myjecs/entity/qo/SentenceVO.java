package com.wlld.myjecs.entity.qo;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wlld.myjecs.entity.KeywordSql;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SentenceVO {
    private int sentence_id;//语句id
    private String word;//语句内容
    private int type_id;//类别id
    //关键词分类对应关键词
    private Dict keyword;
    //关键词种类
    private List<Integer> keywordIds;

}
