package com.wlld.myjecs.controller.vue;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.bean.BeanManger;
import com.wlld.myjecs.bean.BeanMangerOnly;
import com.wlld.myjecs.config.Config;
import com.wlld.myjecs.entity.*;
import com.wlld.myjecs.entity.business.MyKeywordStudy;
import com.wlld.myjecs.entity.business.MySentence;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.entity.qo.SocketMessage;
import com.wlld.myjecs.mapper.SqlMapper;
import com.wlld.myjecs.service.SentenceConfigService;
import com.wlld.myjecs.tools.AssertTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.wlld.entity.TalkBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/sentence/config/vue"})
@Api(value = "config", tags = {"配置管理-vue"})
public class SentenceConfigVueController {
    private final SentenceConfigService sentenceConfigService;
    private final ConfigurableApplicationContext applicationContext;

    private LambdaQueryWrapper<SentenceConfig> buildQuery(SentenceConfig sentenceConfig) {
        LambdaQueryWrapper<SentenceConfig> query = new LambdaQueryWrapper<>();
        query.orderByDesc(SentenceConfig::getId);
        return query;
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping({"/page"})
    public Response page(Page page, SentenceConfig sentenceConfig) {
        return Response.ok(sentenceConfigService.page(page, buildQuery(sentenceConfig)));
    }

    @GetMapping({"/use"})
    public Response use(SentenceConfig sentenceConfig) {
        LambdaUpdateWrapper<SentenceConfig> query = new LambdaUpdateWrapper<>();
        sentenceConfigService.update(query.set(SentenceConfig::getStatus, 1).eq(sentenceConfig.getId() != null, SentenceConfig::getId, sentenceConfig.getId()));
        LambdaUpdateWrapper<SentenceConfig> query1 = new LambdaUpdateWrapper<>();
        sentenceConfigService.update(query1.set(SentenceConfig::getStatus, 0).ne(sentenceConfig.getId() != null, SentenceConfig::getId, sentenceConfig.getId()));
        return Response.ok(null);
    }

    @PostMapping({"/save"})
    public Response save(@RequestBody @Validated SentenceConfig sentenceConfig) {
        log.info("保存:" + JSON.toJSONString(sentenceConfig));
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<SentenceConfig> chainWrapper = new LambdaQueryWrapper<>();
        SentenceConfig exist = sentenceConfigService.getOne(chainWrapper.eq(SentenceConfig::getId, sentenceConfig.getId()));
        if (exist != null) {
            sentenceConfigService.updateById(sentenceConfig);
        } else {
            sentenceConfig.setStatus("0");
            sentenceConfigService.save(sentenceConfig);
        }
        return Response.ok(sentenceConfig);
    }

    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @GetMapping({"/delete"})
    public Response delete(Integer[] ids) {
        sentenceConfigService.removeBatchByIds(CollUtil.newArrayList(ids));
        return Response.ok(null);
    }

    @ApiOperation(value = "训练模型")
    @GetMapping({"/init"})
    @SneakyThrows
    public Response init(SocketMessage socketMessage) {
        // 创建一个异步任务
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            if (SocketMessage.TALK.equals(socketMessage.getType())) {
                Config.TALK_DOING = true;
                initTalk();
                log.info("训练对话完成");
                return Config.TALK_DOING;
            } else if (SocketMessage.SEMANTICS.equals(socketMessage.getType())) {
                Config.SEMANTICS_DOING = true;
                initSemantics();
                log.info("训练语义完成");
                return Config.SEMANTICS_DOING;
            }
            return false;
        });
        return Response.ok(null);
    }

    @ApiOperation(value = "获取训练模型状态")
    @GetMapping({"/getStatus"})
    public Response getStatus(SocketMessage socketMessage) {
        if (SocketMessage.TALK.equals(socketMessage.getType())) {
            return Response.ok(Config.TALK_DOING);
        } else if (SocketMessage.SEMANTICS.equals(socketMessage.getType())) {
            return Response.ok(Config.SEMANTICS_DOING);
        }
        return Response.ok(null);
    }

    /**
     * 初始化对话
     */
    @SneakyThrows
    public void initTalk() {
        BeanMangerOnly beanMangerOnly = applicationContext.getBean(BeanMangerOnly.class);
        SqlMapper sql = applicationContext.getBean(SqlMapper.class);
        List<MyTree> trees = sql.getMyTree();
        org.wlld.config.SentenceConfig sentenceConfig = beanMangerOnly.getConfig();
        BeanUtil.copyProperties(getDbConfig(), sentenceConfig);
        sentenceConfig.setTypeNub(trees.size());
        beanMangerOnly.getWordEmbedding().setConfig(sentenceConfig);
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
        Config.TALK_DOING = false;
    }

    /**
     * 初始化语义分类
     */
    @SneakyThrows
    public void initSemantics() {
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
        org.wlld.config.SentenceConfig sentenceConfig = beanMangerOnly.getConfig();
        BeanUtil.copyProperties(getDbConfig(), sentenceConfig);
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
        Config.SEMANTICS_DOING = false;
    }

    private com.wlld.myjecs.entity.SentenceConfig getDbConfig() {
        SentenceConfigService sentenceConfigService = applicationContext.getBean(SentenceConfigService.class);
        LambdaQueryWrapper<com.wlld.myjecs.entity.SentenceConfig> chainWrapper = new LambdaQueryWrapper<>();
        chainWrapper.eq(com.wlld.myjecs.entity.SentenceConfig::getStatus, "1");
        return sentenceConfigService.getOne(chainWrapper);
    }
}