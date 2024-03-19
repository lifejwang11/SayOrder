package com.wlld.myjecs.controller.vue;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.service.KeywordTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/keywordType/vue"})
@Api(value = "config", tags = {"关键词管理-vue"})
public class KeywordTypeVueController {
    private final KeywordTypeService keywordTypeService;

    private LambdaQueryWrapper<KeywordType> buildQuery(KeywordType keywordType) {
        LambdaQueryWrapper<KeywordType> query = new LambdaQueryWrapper<>();
        return query;
    }

    @ApiOperation(value = "通过类别id查询", notes = "通过类别id查询")
    @GetMapping({"/list"})
    public Response list(KeywordType keywordType) {
        return Response.ok(keywordTypeService.list(new LambdaQueryWrapper<KeywordType>()
                .eq(KeywordType::getType_id, keywordType.getType_id())));
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
            keywordTypeService.save(keywordType);
        }
        return Response.ok(keywordType);
    }

    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @GetMapping({"/delete"})
    public Response delete(Integer[] ids) {
        keywordTypeService.removeBatchByIds(CollUtil.newArrayList(ids));
        return Response.ok(null);
    }
}