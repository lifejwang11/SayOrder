package com.wlld.myjecs.tools;

import lombok.experimental.UtilityClass;
import org.wlld.config.SentenceConfig;

/**
 * @author fn
 * @description
 * @date 2024/4/7 18:54
 */
@UtilityClass
public class ThreadLocalCache {
    private static final ThreadLocal<SentenceConfig> SENTENCE_CONFIG_CACHE = new ThreadLocal<>();

    public SentenceConfig getConfig() {
        return SENTENCE_CONFIG_CACHE.get();
    }
    public void setConfig(SentenceConfig config) {
        SENTENCE_CONFIG_CACHE.set(config);
    }
}
