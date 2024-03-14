package com.wlld.myjecs.mapper;

import com.wlld.myjecs.entity.Sentence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 44223
* @description 针对表【sentence】的数据库操作Mapper
* @createDate 2024-03-14 10:30:35
* @Entity com.wlld.myjecs.entity.Sentence
*/
@Mapper
public interface SentenceMapper extends BaseMapper<Sentence> {

}




