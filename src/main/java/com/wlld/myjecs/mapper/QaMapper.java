package com.wlld.myjecs.mapper;

import com.wlld.myjecs.sqlEntity.Qa;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author laowang
 * @description 针对表【q_a(聊天样本)】的数据库操作Mapper
 * @createDate 2024-03-10 21:26:04
 * @Entity com.wlld.myjecs.sqlEntity.Qa
 */
@Mapper
public interface QaMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Qa record);

    int insertSelective(Qa record);

    Qa selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Qa record);

}
