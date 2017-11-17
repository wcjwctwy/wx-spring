package cn.sf80.wx.check.token.config;

import cn.sf80.wx.check.token.pojo.AccessTokenEntity;
import cn.sf80.wx.check.token.pojo.WxPlatformConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @ConfigurationProperties(prefix = "wx")
    @Bean
    @Scope("prototype")
    public AccessTokenEntity accessTokenEntity(){
        return new AccessTokenEntity();
    }

    @ConfigurationProperties(prefix = "wx")
    @Bean
    public WxPlatformConfig wxPlatformConfig(){
        return new WxPlatformConfig();
    }


}
