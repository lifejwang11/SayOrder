package com.wlld.myjecs;

import com.wlld.myjecs.bean.BeanManger;
import com.wlld.myjecs.bean.BeanMangerOnly;
import com.wlld.myjecs.config.Config;
import com.wlld.myjecs.entity.business.MyKeywordStudy;
import com.wlld.myjecs.entity.business.MySentence;
import com.wlld.myjecs.mapper.SqlMapper;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.entity.Keyword_sql;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.Sentence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.wlld.config.SentenceConfig;
import org.wlld.entity.TalkBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SayOrderApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SayOrderApplication.class, args);
//        if (Config.starModel) {
//            init(applicationContext);
//        }
    }

    private static void init(ConfigurableApplicationContext applicationContext) throws Exception {//初始化启动配置
        List<MySentence> sentences = new ArrayList<>();
        BeanMangerOnly beanMangerOnly = applicationContext.getBean(BeanMangerOnly.class);
        SqlMapper sql = applicationContext.getBean(SqlMapper.class);
        List<MyTree> trees = sql.getMyTree();
        List<KeywordType> keywordTypeList = sql.getKeywordType();
        Map<Integer, List<KeywordType>> kts = beanMangerOnly.getKeyTypes();
        for (KeywordType keywordType : keywordTypeList) {
            int typeID = keywordType.getType_id();
            if (kts.containsKey(typeID)) {
                kts.get(typeID).add(keywordType);
            } else {
                List<KeywordType> k = new ArrayList<>();
                k.add(keywordType);
                kts.put(typeID, k);
            }
        }
        SentenceConfig sentenceConfig = beanMangerOnly.getConfig();
        sentenceConfig.setTypeNub(trees.size());
        beanMangerOnly.getWordEmbedding().setConfig(sentenceConfig);
        beanMangerOnly.getRRNerveManager().init(sentenceConfig);
        if (needReadSql() || Config.selfTest) {//若模型文件不存在则读取数据表重新进行学习
            Map<Integer, MySentence> sentenceMap = new HashMap<>();
            List<Sentence> sentencesList = sql.getModel();
            List<Keyword_sql> keywordSqlList = sql.getKeywordSql();
            for (Sentence sentence : sentencesList) {
                MySentence mySentence = new MySentence();
                mySentence.setType_id(sentence.getType_id());
                mySentence.setWord(sentence.getWord());
                sentences.add(mySentence);
                sentenceMap.put(sentence.getSentence_id(), mySentence);
            }
            for (Keyword_sql keywordSql : keywordSqlList) {
                MyKeywordStudy myKeywordStudy = new MyKeywordStudy();
                myKeywordStudy.setKeyword(keywordSql.getKeyword());
                myKeywordStudy.setKeyword_type_id(keywordSql.getKeyword_type_id());
                int sentence_id = keywordSql.getSentence_id();
                if (sentenceMap.containsKey(sentence_id)) {
                    sentenceMap.get(sentence_id).getMyKeywordStudyList().add(myKeywordStudy);
                } else {
                    throw new Exception("关键词表 keyword_sql sentence_id:" + sentence_id + ",无法在sentence表找到对应的语句 sentence_id:" + sentence_id);
                }
            }
        }
        applicationContext.getBean(BeanManger.class).tools().initSemantics(beanMangerOnly, sentences, Config.selfTest);
        List<TalkBody> talkBodies = null;
        boolean needTalk = needTalkSql();
        if (needTalk) {
            talkBodies = sql.getTalkModel();//数据库模板，用户可自己修改数据库信息
            for (int i = 0; i < talkBodies.size(); i++) {
                TalkBody talkBody = talkBodies.get(i);
                String answer = talkBody.getAnswer();
                String question = talkBody.getQuestion();
                if (answer == null || question == null || answer.isEmpty() || question.isEmpty()) {
                    talkBodies.remove(i);
                    i--;
                }
            }
        }
        if (!needTalk || !talkBodies.isEmpty()) {
            applicationContext.getBean(BeanManger.class).talkTools().initSemantics(beanMangerOnly, talkBodies);
        }
        Config.start = true;
        System.out.println("完成初始化");
    }

    private static boolean needTalkSql() {
        boolean isNeedSReadSql = true;
        File file1 = new File(Config.wordUrl);//词嵌入
        File file2 = new File(Config.talkUrl);//语句模型
        if (file1.exists() && file2.exists()) {
            isNeedSReadSql = false;
        }
        return isNeedSReadSql;
    }

    private static boolean needReadSql() {
        boolean isNeedSReadSql = true;
        File file1 = new File(Config.Word2VecModelUrl);//词嵌入
        File file2 = new File(Config.SentenceModelUrl);//语句模型
        File file3 = new File(Config.onlyKeyWord);//关键词敏感性
        File file4 = new File(Config.KeyWordModelUrl);//关键词捕捉
        File file5 = new File(Config.keyWordIndex);//关键词
        if (file1.exists() && file2.exists() && file3.exists() && file4.exists() && file5.exists()) {
            isNeedSReadSql = false;
        }
        return isNeedSReadSql;
    }
}
