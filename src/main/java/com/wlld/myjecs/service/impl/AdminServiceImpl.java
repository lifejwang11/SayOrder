package com.wlld.myjecs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wlld.myjecs.entity.Admin;
import com.wlld.myjecs.service.AdminService;
import com.wlld.myjecs.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author 44223
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2024-03-14 10:30:09
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




