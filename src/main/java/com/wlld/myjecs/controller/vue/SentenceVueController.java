package com.wlld.myjecs.controller.vue;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.Sentence;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.entity.qo.SentenceVO;
import com.wlld.myjecs.mapper.SentenceMapper;
import com.wlld.myjecs.mapper.SqlMapper;
import com.wlld.myjecs.service.KeywordSqlService;
import com.wlld.myjecs.service.KeywordTypeService;
import com.wlld.myjecs.service.MyTreeService;
import com.wlld.myjecs.service.SentenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/sentence/vue"})
@Api(value = "config", tags = {"语句管理-vue"})
public class SentenceVueController {
    private final SentenceService sentenceService;
    private final SqlMapper sqlMapper;
    private final KeywordSqlService keywordSqlService;
    private final MyTreeService myTreeService;
    private final KeywordTypeService keywordTypeService;
    private final SentenceMapper sentenceMapper;

    private LambdaQueryWrapper<Sentence> buildQuery(Sentence sentence) {
        LambdaQueryWrapper<Sentence> query = new LambdaQueryWrapper<>();
        query.eq(sentence.getType_id() != null, Sentence::getType_id, sentence.getType_id());
        return query;
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping({"/page"})
    public Response page(Page page, KeywordSql query) {
        return Response.ok(sentenceMapper.pageSentence(page, query));
    }

    @GetMapping({"/find"})
    public Response getById(Sentence sentence) {
        return Response.ok(sentenceService.getById(sentence.getSentence_id()));
    }

    @PostMapping({"/save"})
    public Response save(@RequestBody SentenceVO sentence) {
        log.info("保存:" + JSON.toJSONString(sentence));
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Sentence> chainWrapper = new LambdaQueryWrapper<>();
        Sentence exist = sentenceService.getOne(chainWrapper.eq(Sentence::getSentence_id, sentence.getSentence_id()));
        if (exist != null) {
            Sentence toSave = new Sentence();
            toSave.setSentence_id(sentence.getSentence_id());
            toSave.setWord(sentence.getWord());
            sentenceService.updateById(toSave);
        } else {
            Sentence toSave = new Sentence();
            toSave.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
            toSave.setType_id(sentence.getType_id());
            toSave.setWord(sentence.getWord());
            //语句查重
            if (sqlMapper.different(toSave) != null) {
                return Response.fail(500, "语句重复");
            }
            Dict keyword = sentence.getKeyword();
            List<Integer> keywordIds = sentence.getKeywordIds();
            if (CollUtil.isEmpty(keywordIds)) {
                return Response.fail(500, "请选择子分类");
            }
            if (keyword.isEmpty()) {
                return Response.fail(500, "请填写子分类对应的关键字");
            }
            //保存语句
            boolean success = sentenceService.save(toSave);
            int sentenceId = toSave.getSentence_id();
            List<KeywordSql> toSaveSql = new ArrayList<>();
            for (int i = 0; i < keywordIds.size(); i++) {
                Integer keywordId = keywordIds.get(i);
                KeywordSql keywordSql = new KeywordSql();
                keywordSql.setId(null);
                keywordSql.setKeyword_type_id(keywordId);
                keywordSql.setKeyword((String) keyword.get("keyword" + i));
                keywordSql.setSentence_id(sentenceId);
                toSaveSql.add(keywordSql);
                //自增操作（更新关键词类别对应的语句数）
                String column = LambdaUtil.getFieldName(KeywordType::getType_number);
                keywordTypeService.update(new LambdaUpdateWrapper<KeywordType>()
                        .setSql(StrUtil.isNotBlank(column), String.format("`%s` = `%s` + %s", column, column, 1))
                        .eq(KeywordType::getKeyword_type_id, keywordId));
            }
            //批量保存keywordSql
            keywordSqlService.saveBatch(toSaveSql);
            //自增操作（更新分类对应语句数）
            String column = LambdaUtil.getFieldName(MyTree::getSentence_nub);
            myTreeService.update(new LambdaUpdateWrapper<MyTree>()
                    .setSql(StrUtil.isNotBlank(column), String.format("`%s` = `%s` + %s", column, column, 1))
                    .eq(MyTree::getType_id, sentence.getType_id()));
        }
        return Response.ok(sentence);
    }

    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @GetMapping({"/delete"})
    public Response delete(@RequestParam("ids") List<Integer> ids) {
        Map<Integer, List<Sentence>> typeSentences = sentenceService.list(new LambdaQueryWrapper<Sentence>().in(Sentence::getSentence_id, ids)).stream().collect(Collectors.groupingBy(Sentence::getType_id));
        Map<Integer, List<KeywordSql>> ktSentences = keywordSqlService.list(new LambdaQueryWrapper<KeywordSql>().in(KeywordSql::getSentence_id, ids)).stream().collect(Collectors.groupingBy(KeywordSql::getKeyword_type_id));
        //更新关键词对应语句数
        for (Map.Entry<Integer, List<KeywordSql>> entry : ktSentences.entrySet()) {
            Integer id = entry.getKey();
            Integer size = entry.getValue().size();
            String column = LambdaUtil.getFieldName(KeywordType::getType_number);
            //自减操作
            keywordTypeService.update(new LambdaUpdateWrapper<KeywordType>()
                    .setSql(StrUtil.isNotBlank(column), String.format("`%s` = `%s` - %s", column, column,size))
                    .eq(KeywordType::getKeyword_type_id, id).gt(KeywordType::getType_number, size-1));
        }
        //更新语义分类对应语句数
        for (Map.Entry<Integer, List<Sentence>> entry : typeSentences.entrySet()) {
            Integer id = entry.getKey();
            Integer size = entry.getValue().size();
            String column = LambdaUtil.getFieldName(MyTree::getSentence_nub);
            //自减操作
            myTreeService.update(new LambdaUpdateWrapper<MyTree>()
                    .setSql(StrUtil.isNotBlank(column), String.format("`%s` = `%s` - %s", column, column,size))
                    .eq(MyTree::getType_id, id).gt(MyTree::getSentence_nub, size-1));
        }
        //删除keyword_sql
        ids.forEach(id -> {
            keywordSqlService.remove(new LambdaQueryWrapper<KeywordSql>().eq(KeywordSql::getSentence_id, id));
        });
        //批量删除语句
        sentenceService.removeBatchByIds(ids);
        return Response.ok(null);
    }
}