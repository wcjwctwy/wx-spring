package cn.sf80.wx.check.controller;

import cn.sf80.wx.check.token.pojo.WxPlatformConfig;
import cn.sf80.wx.check.token.pojo.AccessTokenEntity;
import cn.sf80.wx.check.token.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class TestLoginController {

    @Autowired
    private WxPlatformConfig wxPlatformConfig;

    @Autowired
    private AccessTokenEntity accessTokenEntity;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${wx.login.redirectUrl}")
    private String redirectUrl;
//第一步：用户同意授权，获取code
    @GetMapping("/auth")
    public String auth(){
        String url =
        UrlUtils.getWxAuthUrl(wxPlatformConfig,redirectUrl,UrlUtils.snsapi_userinfo,"123");
        return "redirect:"+url;
    }
//第二步：通过code换取网页授权access_token
    //特殊的网页授权access_token,与基础支持中的access_token（该access_token用于调用其他接口）不同
    @GetMapping("/login")
    @ResponseBody
    public String login(String code,String state){
        System.out.println(code+"  "+state);
        System.out.println(accessTokenEntity.getAppid());

//        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//        URI uri = URI.create("https://api.weixin.qq.com/sns/oauth2/access_token");
//        RequestEntity<AccessTokenEntity > entity = new RequestEntity<AccessTokenEntity>(accessTokenEntity,HttpMethod.POST,uri);
//        restTemplate.exchange(entity,String.class);
        return "SUCCESS";
    }



}
