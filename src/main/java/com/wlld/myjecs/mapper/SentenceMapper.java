package com.wlld.myjecs.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.Sentence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wlld.myjecs.entity.qo.TreeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 44223
* @description 针对表【sentence】的数据库操作Mapper
* @createDate 2024-03-14 10:30:35
* @Entity com.wlld.myjecs.entity.Sentence
*/
@Mapper
public interface SentenceMapper extends BaseMapper<Sentence> {

    IPage<Sentence> pageSentence(Page<Sentence> page, @Param("query") KeywordSql query);
}




