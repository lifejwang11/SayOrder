package com.wlld.myjecs.controller.vue;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.Sentence;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.mapper.SentenceMapper;
import com.wlld.myjecs.service.SentenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/sentence/vue"})
@Api(value = "config", tags = {"语句管理-vue"})
public class SentenceVueController {
    private final SentenceService sentenceService;
    private final SentenceMapper sentenceMapper;

    private LambdaQueryWrapper<Sentence> buildQuery(Sentence sentence) {
        LambdaQueryWrapper<Sentence> query = new LambdaQueryWrapper<>();
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
    public Response save(@RequestBody Sentence sentence) {
        log.info("保存管理员:" + JSON.toJSONString(sentence));
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Sentence> chainWrapper = new LambdaQueryWrapper<>();
        Sentence exist = sentenceService.getOne(chainWrapper.eq(Sentence::getSentence_id, sentence.getSentence_id()));
        if (exist != null) {
            sentenceService.updateById(sentence);
        } else {
            sentenceService.save(sentence);
        }
        return Response.ok(sentence);
    }

    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @GetMapping({"/delete"})
    public Response delete(Integer[] ids) {
        sentenceService.removeBatchByIds(CollUtil.newArrayList(ids));
        return Response.ok(null);
    }
}