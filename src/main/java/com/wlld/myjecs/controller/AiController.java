package com.wlld.myjecs.controller;


import com.wlld.myjecs.business.AiBusiness;
import com.wlld.myjecs.config.Config;
import com.wlld.myjecs.config.ErrorCode;
import com.wlld.myjecs.mesEntity.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
@RestController
@RequestMapping("/ai")
@Api(tags = "语言服务")
public class AiController {
    @Autowired
    private AiBusiness business;

    @RequestMapping(value = "/talk", method = RequestMethod.POST)//
    @ApiOperation("语义识别对话")
    @ApiImplicitParam(name = "sentence", value = "用户对话语句", defaultValue = "给我找个保洁", required = true)
    public Response talk(@RequestBody String sentence) throws Exception {
        Response response;
        if (Config.starModel) {
            if (Config.start) {
                response = business.talk(sentence);
            } else {
                response = new Response();
                response.setError(ErrorCode.ModelNotStart.getError());
                response.setErrorMessage(ErrorCode.ModelNotStart.getErrorMessage());
            }
        } else {
            response = new Response();
            response.setError(ErrorCode.notStartModel.getError());
            response.setErrorMessage(ErrorCode.notStartModel.getErrorMessage());
        }
        return response;
    }

    @RequestMapping(value = "/myTalk", method = RequestMethod.POST)//
    @ApiOperation("聊天")
    @ApiImplicitParam(name = "sentence", value = "用户对话语句", defaultValue = "广告可以退款么", required = true)
    public Response myTalk(@RequestBody String sentence) throws Exception {
        Response response;
        if (Config.starModel) {
            if (Config.start) {
                response = business.myTalk(sentence);
            } else {
                response = new Response();
                response.setError(ErrorCode.ModelNotStart.getError());
                response.setErrorMessage(ErrorCode.ModelNotStart.getErrorMessage());
            }
        } else {
            response = new Response();
            response.setError(ErrorCode.notStartModel.getError());
            response.setErrorMessage(ErrorCode.notStartModel.getErrorMessage());
        }
        return response;
    }
}
