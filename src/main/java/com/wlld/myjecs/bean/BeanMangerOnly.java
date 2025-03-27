package com.wlld.myjecs.bean;


import com.wlld.myjecs.config.FreeWord;
import com.wlld.myjecs.entity.business.AllKeyWords;
import com.wlld.myjecs.config.SysConfig;
import com.wlld.myjecs.entity.KeywordType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.dromara.easyai.config.RZ;
import org.dromara.easyai.config.SentenceConfig;
import org.dromara.easyai.config.TfConfig;
import org.dromara.easyai.naturalLanguage.TalkToTalk;
import org.dromara.easyai.naturalLanguage.languageCreator.CatchKeyWord;
import org.dromara.easyai.naturalLanguage.word.MyKeyWord;
import org.dromara.easyai.naturalLanguage.word.WordEmbedding;
import org.dromara.easyai.rnnJumpNerveCenter.CustomManager;
import org.dromara.easyai.rnnJumpNerveCenter.RRNerveManager;

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
        sentenceConfig.setQaWordVectorDimension(32);//维度大参数多，它就能适应更复杂的情况，速度越慢，需要样本越多
        sentenceConfig.setMaxWordLength(20);//语言长度 越长越好，但是越长需求的数据量越大，计算时间越长性能越差，也需要更多的内存。
        sentenceConfig.setTrustPowerTh(0);//语义分类可信阈值，范围0-1
        sentenceConfig.setSentenceTrustPowerTh(0.3f);//生成语句可信阈值
        sentenceConfig.setWeStudyPoint(0.005f);
        sentenceConfig.setMaxAnswerLength(50);//回复语句的最长长度
        sentenceConfig.setTimes(1);//qa模型训练增强
        sentenceConfig.setParam(0.3f);//正则抑制系数
        return sentenceConfig;
    }

    @Bean
    public TfConfig getTfConfig() {//64-128
        TfConfig tfConfig = new TfConfig();
        tfConfig.setTimes(200);//样本量不足，增加训练量
        tfConfig.setMultiNumber(8);
        tfConfig.setAllDepth(1);
        tfConfig.setMaxLength(50);
        tfConfig.setStudyPoint(0.001f);
        tfConfig.setSplitWord(null);
        tfConfig.setSelfTimeCode(true);
        tfConfig.setShowLog(true);
        return tfConfig;
    }

    @Bean
    public TalkToTalk getTalkToTalk() throws Exception {
        return new TalkToTalk(getEmbedding(), getTfConfig());
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
        WordEmbedding wordEmbedding = new WordEmbedding();
        wordEmbedding.setStudyTimes(1);
        return wordEmbedding;
    }

    @Bean
    public CustomManager getCustomManager() throws Exception {
        return new CustomManager(getEmbedding(), getConfig());
    }

}
