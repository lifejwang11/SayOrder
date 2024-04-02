package com.wlld.myjecs.bean;

import com.wlld.myjecs.config.SayOrderConfig;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.entity.mes.Shop;
import com.wlld.myjecs.tools.TalkTools;
import com.wlld.myjecs.tools.Tools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
@Configuration
@Scope("prototype")
public class BeanManger {
    @Bean
    public Response response() {
        return new Response();
    }

    @Bean
    public Shop shop() {
        return new Shop();
    }

    @Bean
    public Tools tools(SayOrderConfig sayOrderConfig) {
        return new Tools(sayOrderConfig);
    }

    @Bean
    public TalkTools talkTools(SayOrderConfig sayOrderConfig) {
        return new TalkTools(sayOrderConfig);
    }
}
