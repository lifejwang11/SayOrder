package com.wlld.myjecs.bean;

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
    public Tools tools() {
        return new Tools();
    }

    @Bean
    public TalkTools talkTools() {
        return new TalkTools();
    }
}
