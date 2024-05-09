package com.wlld.myjecs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.Sentence;
import com.wlld.myjecs.service.SentenceService;
import com.wlld.myjecs.mapper.SentenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 44223
* @description 针对表【sentence】的数据库操作Service实现
* @createDate 2024-03-14 10:30:35
*/
@Service
public class SentenceServiceImpl extends ServiceImpl<SentenceMapper, Sentence>
    implements SentenceService{
    @Autowired
    private SentenceMapper sentenceMapper;

    @Override
    public List<Sentence> listByKeyWord(KeywordSql query) {
        return sentenceMapper.listByKeyWord(query);
    }

    @Override
    public List<Sentence> listByOrders(KeywordSql query, List<Integer> ktIds) {
        return sentenceMapper.listByOrders(query,ktIds);
    }
}




