package com.wlld.myjecs.controller.vue;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.mes.Response;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/keywordType/vue"})
@Api(value = "config", tags = {"关键词管理-vue"})
public class KeywordTypeVueController {
    private final KeywordTypeService keywordTypeService;
    private final KeywordSqlService keywordSqlService;
    private final SentenceService sentenceService;
    private final MyTreeService myTreeService;

    private LambdaQueryWrapper<KeywordType> buildQuery(KeywordType keywordType) {
        LambdaQueryWrapper<KeywordType> query = new LambdaQueryWrapper<>();
        return query;
    }

    @ApiOperation(value = "通过类别id查询", notes = "通过类别id查询")
    @GetMapping({"/list"})
    public Response list(KeywordType keywordType) {
        return Response.ok(keywordTypeService.list(new LambdaQueryWrapper<KeywordType>()
                .eq(keywordType.getType_id() != null, KeywordType::getType_id, keywordType.getType_id())));
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping({"/page"})
    public Response page(Page page, KeywordType keywordType) {
        return Response.ok(keywordTypeService.page(page, buildQuery(keywordType)));
    }

    @GetMapping({"/find"})
    public Response getById(KeywordType keywordType) {
        return Response.ok(keywordTypeService.getById(keywordType.getKeyword_type_id()));
    }

    @PostMapping({"/save"})
    public Response save(@RequestBody KeywordType keywordType) {
        log.info("保存管理员:" + JSON.toJSONString(keywordType));
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<KeywordType> chainWrapper = new LambdaQueryWrapper<>();
        KeywordType exist = keywordTypeService.getOne(chainWrapper.eq(KeywordType::getKeyword_type_id, keywordType.getKeyword_type_id()));
        if (exist != null) {
            keywordTypeService.updateById(keywordType);
        } else {
            keywordType.setType_number(0);
            keywordTypeService.save(keywordType);
        }
        return Response.ok(keywordType);
    }

    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @GetMapping({"/delete"})
    public Response delete(Integer[] ids) {
        //逻辑只包含单个ID删除逻辑。多个需要重构
        List<Integer> ktIds = CollUtil.newArrayList(ids);
        LambdaQueryWrapper<KeywordSql> sqlQuery = new LambdaQueryWrapper<>();
        sqlQuery.in(KeywordSql::getKeyword_type_id, ids);
        List<KeywordSql> sqlList = keywordSqlService.list(sqlQuery);
        List<Integer> sentenceIds = sqlList.stream().map(KeywordSql::getSentence_id).distinct().collect(Collectors.toList());

        LambdaQueryWrapper<KeywordSql> assertSqlQuery = new LambdaQueryWrapper<>();
        assertSqlQuery.in(KeywordSql::getSentence_id, sentenceIds);
        List<KeywordSql> assertSqlList = keywordSqlService.list(assertSqlQuery);
        Map<Integer, List<KeywordSql>> groupKt = assertSqlList.stream().collect(Collectors.groupingBy(KeywordSql::getKeyword_type_id));
        //语句只关联一个关键词的情况下执行下述逻辑
        if (groupKt.size() == 1) {
            //删除对应关键词对应的所有语句
            sentenceService.removeBatchByIds(sentenceIds);
            //自减操作（更新分类对应语句数）
            int size = sentenceIds.size();
            String column = LambdaUtil.getFieldName(MyTree::getSentence_nub);
            LambdaQueryWrapper<KeywordType> typeQuery = new LambdaQueryWrapper<>();
            List<KeywordType> types = keywordTypeService.list(typeQuery);
            myTreeService.update(new LambdaUpdateWrapper<MyTree>()
                    .setSql(StrUtil.isNotBlank(column), String.format("`%s` = `%s` - %s", column, column, size))
                    .eq(MyTree::getType_id, types.get(0).getType_id()).gt(MyTree::getSentence_nub, size - 1))
            ;
        }
        //删除关键词
        keywordTypeService.removeBatchByIds(ktIds);
        //删除语句与关键词的关联
        keywordSqlService.remove(sqlQuery);
        return Response.ok(null);
    }
}