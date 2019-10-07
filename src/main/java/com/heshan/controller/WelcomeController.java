package com.heshan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangbingquan
 * @desc HelloWorld欢迎页面
 * @time 2019-10-07 19:26
 */
@Controller
public class WelcomeController {


    //打开首页
    @RequestMapping("/")
    public String pageIndex() {
        return "index";
    }



}
