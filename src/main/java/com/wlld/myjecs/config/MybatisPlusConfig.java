package com.wlld.myjecs.config;

import cn.hutool.core.net.NetUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.wlld.myjecs.handler.InjectionMetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

/**
 * MybatisPlus 插件配置
 */
@Slf4j
@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class MybatisPlusConfig {

    /**
     * mybatis plus 拦截器配置
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor( List<HandlerMethodArgumentResolver> argumentResolvers) {
        log.info(">>>>>>>>>>> 加载mybatis-plus 拦截器 >>>>>>>>>>> ");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();


        // 分页插件
        log.info("加载mybatis-plus 分页插件");
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(-1L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        log.info("加载mybatis-plus 乐观锁插件");
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        log.info(">>>>>>>>>>> 加载mybatis-plus 拦截器 >>>>>>>>>>> ");
        return interceptor;
    }

    /**
     * 元对象字段填充控制器
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new InjectionMetaObjectHandler();
    }

    /**
     * 使用网卡信息绑定雪花生成器
     * 防止集群雪花ID重复
     */
    @Bean
    public IdentifierGenerator idGenerator() {
        return new DefaultIdentifierGenerator(NetUtil.getLocalhost());
    }

}
