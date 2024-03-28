package com.wlld.myjecs.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlld.myjecs.mapper.QaMapper;
import com.wlld.myjecs.entity.Qa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Laowang
 */
@Slf4j
@Service
public class QuestionService {


    @Autowired
    private QaMapper qaMapper;

    public PageInfo<Qa> findAll(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Qa> all = qaMapper.findAll(pageIndex, pageSize);
        return new PageInfo<>(all);
    }

    public void delById(Integer qaId) {
        qaMapper.delById(qaId);
    }

    public void upById(Qa qa) {
        qaMapper.updateById(qa);
    }

    public void insert(Qa qa) {
        qaMapper.insert(qa);
    }

    public Qa selectById(Integer qaId) {
        return qaMapper.selectById(qaId);
    }
}
