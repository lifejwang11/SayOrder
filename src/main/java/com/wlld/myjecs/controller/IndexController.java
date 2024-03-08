package com.wlld.myjecs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author a1-6
 */
@Controller
@RequestMapping("/api")
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);


    @RequestMapping("/to_login")
    public String toLogin() {
        return "index";
    }

    @RequestMapping("/to_admin")
    public String toAdmin(@RequestParam(name = "tokenID") String tokenID) {
        logger.info(">>>>>> toAdmin tokenID={}",tokenID);
        return "admin";
    }

    @RequestMapping("/to_worker")
    public String toWorker(@RequestParam(name = "tokenID") String tokenID) {
        logger.info(">>>>>> toWorker tokenID={}",tokenID);
        return "worker";
    }
}
