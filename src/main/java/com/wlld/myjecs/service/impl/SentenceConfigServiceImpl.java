package com.wlld.myjecs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wlld.myjecs.entity.SentenceConfig;
import com.wlld.myjecs.service.SentenceConfigService;
import com.wlld.myjecs.mapper.SentenceConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 44223
* @description 针对表【sentence_config(训练配置表)】的数据库操作Service实现
* @createDate 2024-03-25 18:14:09
*/
@Service
public class SentenceConfigServiceImpl extends ServiceImpl<SentenceConfigMapper, SentenceConfig>
    implements SentenceConfigService{
    @Autowired
    private SentenceConfigMapper sentenceConfigMapper;

    @Override
    public SentenceConfig getConfig() {
        LambdaQueryWrapper<SentenceConfig> chainWrapper = new LambdaQueryWrapper<>();
        chainWrapper.eq(SentenceConfig::getStatus, "1");
        return sentenceConfigMapper.selectOne(chainWrapper);
    }
}




