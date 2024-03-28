package com.wlld.myjecs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wlld.myjecs.entity.Sentence;
import com.wlld.myjecs.service.SentenceService;
import com.wlld.myjecs.mapper.SentenceMapper;
import org.springframework.stereotype.Service;

/**
* @author 44223
* @description 针对表【sentence】的数据库操作Service实现
* @createDate 2024-03-14 10:30:35
*/
@Service
public class SentenceServiceImpl extends ServiceImpl<SentenceMapper, Sentence>
    implements SentenceService{

}




