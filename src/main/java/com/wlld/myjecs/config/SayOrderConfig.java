package com.wlld.myjecs.config;

import cn.hutool.core.io.FileUtil;
import com.wlld.myjecs.tools.AssertTools;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author fn
 * @description
 * @date 2024/3/29 17:00
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "say-order")
public class SayOrderConfig {
    /**
     * 模型基本目录
     */
    private String baseDir;

    public String getBaseDir() {
        if (AssertTools.checkPathValid(baseDir)) {
            //目录不存在创建目录
            FileUtil.mkdir(new File(baseDir));
        }
        return baseDir;
    }
}
