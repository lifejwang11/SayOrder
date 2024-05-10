package com.wlld.myjecs.bean;


import com.wlld.myjecs.config.FreeWord;
import com.wlld.myjecs.entity.business.AllKeyWords;
import com.wlld.myjecs.config.SysConfig;
import com.wlld.myjecs.entity.KeywordType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wlld.config.SentenceConfig;
import org.wlld.naturalLanguage.languageCreator.CatchKeyWord;
import org.wlld.naturalLanguage.word.MyKeyWord;
import org.wlld.naturalLanguage.word.WordEmbedding;
import org.wlld.rnnJumpNerveCenter.CustomManager;
import org.wlld.rnnJumpNerveCenter.RRNerveManager;

import java.util.*;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
@Configuration
public class BeanMangerOnly {//需要单例的类

    @Bean
    public SentenceConfig getConfig() {//配置文件
        SentenceConfig sentenceConfig = new SentenceConfig();
        sentenceConfig.setMaxWordLength(20);//语言长度 越长越好，但是越长需求的数据量越大，计算时间越长性能越差，也需要更多的内存。
        sentenceConfig.setTrustPowerTh(0.5);//语义分类可信阈值，范围0-1
        sentenceConfig.setSentenceTrustPowerTh(0.3);//生成语句可信阈值
        sentenceConfig.setMaxAnswerLength(20);//回复语句的最长长度
        sentenceConfig.setTimes(8);//qa模型训练增强
        sentenceConfig.setParam(0.3);//正则抑制系数
        return sentenceConfig;
    }

    @Bean
    public List<String> getFreeWord() {
        return new ArrayList<>(Arrays.asList(FreeWord.keyWord));
    }

    @Bean
    public WordEmbedding getWordEmbedding() {//词向量嵌入器（word2Vec）
        //中文语句 你，我，他，好 文字 是 离散的高维特征->离散特征连续化 并降维 [0.223,0.123,0.122334 ....]
        return new WordEmbedding();
    }

    @Bean
    public RRNerveManager getRRNerveManager() throws Exception {
        return new RRNerveManager(getWordEmbedding());
    }

    @Bean//关键词抓取类
    public Map<Integer, CatchKeyWord> catchKeyWord() {//关键词抓取
        return new HashMap<>();
    }

    @Bean
    public Map<Integer, MyKeyWord> getMyKeyWord() {//关键词敏感性嗅探
        // return new MyKeyWord(getConfig(), getWordEmbedding());
        return new HashMap<>();
    }

    @Bean
    public Map<Integer, List<KeywordType>> getKeyTypes() {//主键是语句类别，值是找个语句类别的关键词种类集合
        return new HashMap<>();
    }

    @Bean
    public AllKeyWords getAllKeyWords() {//获取所有关键词
        return new AllKeyWords();
    }

    @Bean
    public SysConfig sysConfig() {
        return new SysConfig();
    }

    @Bean
    public WordEmbedding getEmbedding() {//词向量嵌入器（word2Vec）
        return new WordEmbedding();
    }

    @Bean
    public CustomManager getCustomManager() throws Exception {
        return new CustomManager(getEmbedding(), getConfig());
    }

}
