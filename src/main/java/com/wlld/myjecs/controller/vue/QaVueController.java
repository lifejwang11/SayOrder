package com.wlld.myjecs.controller.vue;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.entity.Qa;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.service.QaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/qa/vue"})
@Api(value = "config", tags = {"问题管理-vue"})
public class QaVueController {
    private final QaService qaService;

    private LambdaQueryWrapper<Qa> buildQuery(Qa qa) {
        LambdaQueryWrapper<Qa> query = new LambdaQueryWrapper<>();
        query.like(StrUtil.isNotBlank(qa.getAnswer()), Qa::getAnswer, qa.getAnswer());
        query.like(StrUtil.isNotBlank(qa.getQuestion()), Qa::getQuestion, qa.getQuestion());
        query.orderByDesc(Qa::getId);
        return query;
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping({"/page"})
    public Response page(Page page, Qa qa) {
        return Response.ok(qaService.page(page, buildQuery(qa)));
    }

    @GetMapping({"/find"})
    public Response getById(Qa qa) {
        return Response.ok(qaService.getById(qa.getId()));
    }

    @PostMapping({"/save"})
    public Response save(@RequestBody Qa qa) {
        log.info("保存管理员:" + JSON.toJSONString(qa));
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Qa> chainWrapper = new LambdaQueryWrapper<>();
        Qa exist = qaService.getOne(chainWrapper.eq(Qa::getId, qa.getId()));
        if (exist != null) {
            qaService.updateById(qa);
        } else {
            qaService.save(qa);
        }
        return Response.ok(qa);
    }

    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @GetMapping({"/delete"})
    public Response delete(Integer[] ids) {
        qaService.removeBatchByIds(CollUtil.newArrayList(ids));
        return Response.ok(null);
    }
}