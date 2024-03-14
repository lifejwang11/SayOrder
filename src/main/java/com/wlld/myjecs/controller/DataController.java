package com.wlld.myjecs.controller;

import com.wlld.myjecs.business.DataBusiness;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.entity.mes.SentenceTypeAndKeyword;
import com.wlld.myjecs.entity.mes.SubmitSentence;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/myData")
@Api(tags = "数据标注")
public class DataController {
    @Autowired
    private DataBusiness dataBusiness;

    @RequestMapping(value = "/addSentence", method = RequestMethod.POST)//
    @ApiOperation("添加语句标注")
    public Response addSentence(@RequestBody SubmitSentence submitSentence, HttpServletResponse response) {
        return dataBusiness.addSentence(submitSentence, response);
    }

    @RequestMapping(value = "/addSentenceType", method = RequestMethod.POST)//
    @ApiOperation("添加语句与关键词类别")
    public Response addSentenceType(HttpServletResponse request, @RequestBody SentenceTypeAndKeyword sentenceTypeAndKeyword) {
        return dataBusiness.addSentenceType(request, sentenceTypeAndKeyword);
    }

    @RequestMapping(value = "/delSentenceType", method = RequestMethod.GET)//
    @ApiOperation("删除语句类别")
    public Response delSentenceType(HttpServletResponse request, int type_id) {
        return dataBusiness.delSentenceType(request, type_id);
    }

    @RequestMapping(value = "/delSentence", method = RequestMethod.GET)//
    @ApiOperation("删除语句")
    public Response delSentence(HttpServletResponse res, int sentence_id) {
        return dataBusiness.delSentence(res, sentence_id);
    }

    @RequestMapping(value = "/delKeywordType", method = RequestMethod.GET)//
    @ApiOperation("删除指定id关键词")
    public Response delKeywordType(HttpServletResponse request, int keyword_type_id) {
        return dataBusiness.delKeywordType(request, keyword_type_id);
    }

    // TODO: 2024/3/10 qa的增删改查
}
