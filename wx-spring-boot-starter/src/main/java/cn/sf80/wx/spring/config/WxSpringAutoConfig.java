package cn.sf80.wx.spring.config;

import cn.sf80.wx.spring.pojo.WxPlatformConfig;
import cn.sf80.wx.spring.WxUserInfoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WxPlatformConfig.class)
@ConditionalOnBean(WxUserInfoHandler.class)
public class WxSpringAutoConfig {

    @Autowired
    private WxPlatformConfig wxPlatformConfig;

    @Bean
    public WxSpringServer getWxSpringServer(){
        WxSpringServer wxSpringServer = new WxSpringServer();
        wxSpringServer.setWxPlatformConfig(wxPlatformConfig);
        return wxSpringServer;
    }
//    @Bean
//    public WxUserInfoHandler WxUserInfoHandler(){
//        return wxUserInfo -> System.out.println("Refresh_token  :"+wxUserInfo.getRefresh_token());
//    }


}
