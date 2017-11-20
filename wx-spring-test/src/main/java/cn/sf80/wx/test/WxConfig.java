package cn.sf80.wx.test;

import cn.sf80.wx.spring.WxUserInfoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {

    @Bean
    public WxUserInfoHandler getWxUserInfoHandler(){
        return wxUserInfo -> System.out.println("用户名称："+wxUserInfo.getNickname());
    }

}
