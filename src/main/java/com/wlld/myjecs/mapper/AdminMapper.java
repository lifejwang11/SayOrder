package com.wlld.myjecs.mapper;

import com.wlld.myjecs.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 44223
* @description 针对表【admin】的数据库操作Mapper
* @createDate 2024-03-14 10:30:09
* @Entity com.wlld.myjecs.entity.Admin
*/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}




