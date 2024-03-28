package com.wlld.myjecs.controller.vue;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.Sentence;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.entity.qo.TreeQuery;
import com.wlld.myjecs.mapper.MyTreeMapper;
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
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/tree/vue"})
@Api(value = "config", tags = {"语义类别管理-vue"})
public class MyTreeVueController {
    private final MyTreeService myTreeService;
    private final KeywordSqlService keywordSqlService;
    private final KeywordTypeService keywordTypeService;
    private final SentenceService sentenceService;
    private final MyTreeMapper myTreeMapper;

    private LambdaQueryWrapper<MyTree> buildQuery(TreeQuery tree) {
        LambdaQueryWrapper<MyTree> query = new LambdaQueryWrapper<>();
        return query;
    }
    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping({"/list"})
    public Response list() {
        return Response.ok(myTreeService.list());
    }
    @ApiOperation(value = "语义类别及对应关键词分组树")
    @GetMapping({"/groupTree"})
    public Response groupTree() {
        return Response.ok(myTreeMapper.groupTree());
    }
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping({"/page"})
    public Response page(Page<MyTree> page, TreeQuery query) {
        return Response.ok(myTreeMapper.pageTree(page, query));
    }

    @GetMapping({"/find"})
    public Response getById(MyTree tree) {
        return Response.ok(myTreeService.getById(tree.getType_id()));
    }

    @PostMapping({"/save"})
    public Response save(@RequestBody MyTree tree) {
        log.info("保存管理员:" + JSON.toJSONString(tree));
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<MyTree> chainWrapper = new LambdaQueryWrapper<>();
        MyTree exist = myTreeService.getOne(chainWrapper.eq(MyTree::getType_id, tree.getType_id()));
        if (exist != null) {
            myTreeService.updateById(tree);
        } else {
            myTreeService.save(tree);
        }
        return Response.ok(tree);
    }

    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @GetMapping({"/delete"})
    public Response delete(Integer[] ids) {
        List<Integer> typeIds = CollUtil.newArrayList(ids);
        keywordTypeService.list(new LambdaQueryWrapper<KeywordType>().in(KeywordType::getType_id, typeIds)).forEach(kt -> {
            keywordSqlService.remove(new LambdaQueryWrapper<KeywordSql>().eq(KeywordSql::getKeyword_type_id, kt.getKeyword_type_id()));
        });
        typeIds.forEach(id -> {
            keywordTypeService.remove(new LambdaQueryWrapper<KeywordType>().eq(KeywordType::getType_id, id));
            sentenceService.remove(new LambdaQueryWrapper<Sentence>().eq(Sentence::getType_id, id));
            myTreeService.remove(new LambdaQueryWrapper<MyTree>().eq(MyTree::getType_id, id));
        });
        return Response.ok(null);
    }
}