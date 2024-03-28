package com.wlld.myjecs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wlld.myjecs.entity.Qa;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author laowang
 * @description 针对表【q_a(聊天样本)】的数据库操作Mapper
 * @createDate 2024-03-10 21:26:04
 * @Entity com.wlld.myjecs.sqlEntity.Qa
 */
@Mapper
public interface QaMapper extends BaseMapper<Qa> {

    /**
     * 分页查询
     *
     * @return
     */
    List<Qa> findAll(Integer pageIndex, Integer pageSize);

    void delById(@Param("qaId") Integer qaId);

    @Override
    int insert(Qa record);
}
