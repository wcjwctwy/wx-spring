package cn.sf80.wx.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WxTestController {

    @RequestMapping("/test")
    public String test(){
        return "index";
    }

}
