package com.lzh.sys.controller;

import com.lzh.sys.annotation.Excel;
import com.lzh.sys.pojo.User;
import com.lzh.sys.server.impl.UserServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述
 *
 * @author lzh
 * @date 2019/8/31
 * @description 测试请求
 */
@Controller
public class TestController {
    @Autowired
    private UserServerImpl userServer;

    @GetMapping("/test")
    public String testMapping(Model model){
        model.addAttribute("tips","该用户没有权限无法登录");
        return "pages/login";
    }
}
