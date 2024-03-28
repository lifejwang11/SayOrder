package com.wlld.myjecs.controller;

import com.wlld.myjecs.business.AdminBusiness;
import com.wlld.myjecs.entity.mes.AgreeAdmin;
import com.wlld.myjecs.entity.mes.MyAdmin;
import com.wlld.myjecs.entity.mes.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/admin")
@Api(tags = "成员管理")
public class AdminController {
    @Autowired
    private AdminBusiness adminBusiness;

    @RequestMapping(value = "/register", method = RequestMethod.POST)//
    @ApiOperation("注册账号")
    public Response talk(@RequestBody MyAdmin myAdmin) {
        return adminBusiness.saveUser(myAdmin);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)//
    @ApiOperation("登录")
    public Response login(@RequestBody MyAdmin myAdmin, HttpServletResponse response, HttpServletRequest request) {
        return adminBusiness.login(myAdmin, response, request);
    }

    @RequestMapping(value = "/getInitMessage", method = RequestMethod.GET)//
    @ApiOperation("获取初始化信息")
    public Response getInitMessage(HttpServletResponse request) {
        return adminBusiness.getInitMessage(request);
    }

    @RequestMapping(value = "/pass", method = RequestMethod.POST)//
    @ApiOperation("通过或者否定标注员")
    public Response agreeAdmin(HttpServletResponse request, @RequestBody AgreeAdmin agreeAdmin) {
        return adminBusiness.agree(request, agreeAdmin);
    }

}
