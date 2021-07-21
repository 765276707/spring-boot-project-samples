package com.aurora.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

/**
 * 页面跳转控制器
 * @author xzbcode
 */
@Controller
public class PageController {

    @GetMapping("/index")
    public String toIndexPage(Model model) {
        model.addAttribute("username", "user001");
        model.addAttribute("gender", "男");
        model.addAttribute("birthday", new Date());
        return "/index";
    }

}
