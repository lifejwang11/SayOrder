package com.wlld.myjecs;

import com.wlld.myjecs.bean.BeanManger;
import com.wlld.myjecs.bean.BeanMangerOnly;
import com.wlld.myjecs.config.Config;
import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.Sentence;
import com.wlld.myjecs.entity.business.MyKeywordStudy;
import com.wlld.myjecs.entity.business.MySentence;
import com.wlld.myjecs.mapper.SqlMapper;
import com.wlld.myjecs.service.WebSocketService;
import com.wlld.myjecs.tools.AssertTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.wlld.config.SentenceConfig;
import org.wlld.entity.TalkBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SayOrderApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SayOrderApplication.class, args);
        //创建websocket实例
        WebSocketService.setApplicationContext(applicationContext);
        if (Config.starModel) {
            init(applicationContext);
        }
    }

    /**
     * @param applicationContext
     * @throws Exception
     */

    public static void init(ConfigurableApplicationContext applicationContext) throws Exception {//初始化启动配置
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
        if (AssertTools.needReadSql() || Config.selfTest) {//若模型文件不存在则读取数据表重新进行学习
            Map<Integer, MySentence> sentenceMap = new HashMap<>();
            List<Sentence> sentencesList = sql.getModel();
            List<KeywordSql> keywordSqlList = sql.getKeywordSql();
            for (Sentence sentence : sentencesList) {
                MySentence mySentence = new MySentence();
                mySentence.setType_id(sentence.getType_id());
                mySentence.setWord(sentence.getWord());
                sentences.add(mySentence);
                sentenceMap.put(sentence.getSentence_id(), mySentence);
            }
            for (KeywordSql keywordSql : keywordSqlList) {
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
        boolean needTalk = AssertTools.needTalkSql();
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

}
