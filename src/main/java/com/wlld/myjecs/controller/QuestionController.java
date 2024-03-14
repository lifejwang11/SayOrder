package com.wlld.myjecs.controller;

import com.wlld.myjecs.business.QuestionService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : Laowang
 * @create 2024/3/12
 */
@Controller
@RequestMapping("/question")
@Api(tags = "问答")
public class QuestionController {
    @Resource
    private QuestionService questionService;

}
