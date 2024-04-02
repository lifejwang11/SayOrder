package com.wlld.myjecs.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
}
