package com.wlld.myjecs.service;

import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.Sentence;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 44223
* @description 针对表【sentence】的数据库操作Service
* @createDate 2024-03-14 10:30:35
*/
public interface SentenceService extends IService<Sentence> {
    List<Sentence> listByKeyWord(KeywordSql query);
    List<Sentence> listByOrders(KeywordSql query, List<Integer> ktIds);
}
