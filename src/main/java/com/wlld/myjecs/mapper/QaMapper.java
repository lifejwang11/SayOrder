package com.wlld.myjecs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wlld.myjecs.entity.Qa;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 44223
* @description 针对表【q_a(聊天样本)】的数据库操作Mapper
* @createDate 2024-03-14 10:31:22
* @Entity com.wlld.myjecs.entity.Qa
*/
@Mapper
public interface QaMapper extends BaseMapper<Qa> {

}




