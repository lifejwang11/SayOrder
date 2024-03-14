package com.wlld.myjecs.business;
import com.wlld.myjecs.mapper.QaMapper;
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
}
