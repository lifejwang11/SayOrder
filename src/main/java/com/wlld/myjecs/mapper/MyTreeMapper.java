package com.wlld.myjecs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.qo.TreeQuery;
import com.wlld.myjecs.entity.vo.TreeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 44223
 * @description 针对表【my_tree】的数据库操作Mapper
 * @createDate 2024-03-18 15:48:08
 * @Entity com.wlld.myjecs.entity.MyTree
 */
@Mapper
public interface MyTreeMapper extends BaseMapper<MyTree> {
    IPage<MyTree> pageTree(Page<MyTree> page, @Param("tree") TreeQuery query);
    List<TreeVo> groupTree();
}




