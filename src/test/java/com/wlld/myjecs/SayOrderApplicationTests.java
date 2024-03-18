package com.wlld.myjecs;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.qo.TreeQuery;
import com.wlld.myjecs.mapper.MyTreeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class SayOrderApplicationTests {
    @Autowired
    private MyTreeMapper myTreeMapper;

    @Test
    void testMyTreeMapper() {
        Page<MyTree> page = Page.of(2, 1);
        IPage<MyTree> myTrees = myTreeMapper.pageTree(page, TreeQuery.builder()
                        .title("务")
                .build());
        log.info("MyTreeMapper:{}", myTrees);

    }

    @Test
    void contextLoads() {

    }

}
