package cn.sf80.wx.check.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestMenuController {

    @RequestMapping("/test")
    public String test(){
        return "index";
    }

}
