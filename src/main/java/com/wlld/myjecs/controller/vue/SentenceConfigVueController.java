package com.wlld.myjecs.controller.vue;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.SayOrderApplication;
import com.wlld.myjecs.entity.SentenceConfig;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.service.SentenceConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/sentence/config/vue"})
@Api(value = "config", tags = {"配置管理-vue"})
public class SentenceConfigVueController{
    private final SentenceConfigService sentenceConfigService;
    private final ConfigurableApplicationContext configurableApplicationContext;

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
    public Response init() {
        try {
            SayOrderApplication.init(configurableApplicationContext, true);
        } catch (IndexOutOfBoundsException e) {
            return Response.fail(500, "初始化失败,语义类型变化，请删除模型文件重新训练");
        } catch (Exception e) {
            log.error("初始化异常");
            return Response.fail(500, "初始化失败");
        }
        return Response.ok(null);
    }
}