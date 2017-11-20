package cn.sf80.wx.check.token.controller;

import cn.sf80.wx.check.token.WxUserInfoHandler;
import cn.sf80.wx.check.token.pojo.WxPlatformConfig;
import cn.sf80.wx.check.token.pojo.AccessTokenEntity;
import cn.sf80.wx.check.token.pojo.WxUserInfo;
import cn.sf80.wx.check.token.utils.HttpsUtil;
import cn.sf80.wx.check.token.utils.UrlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@ConditionalOnBean(WxUserInfoHandler.class)
@Controller
@Configuration
public class TestLoginController {

    @Autowired
    private WxPlatformConfig wxPlatformConfig;

    @Autowired
    private AccessTokenEntity accessTokenEntity;

    @Autowired
    private WxUserInfoHandler wxUserInfoHandler;

    @Value("${wx.login.redirectUrl}")
    private String redirectUrl;
//第一步：用户同意授权，获取code
    @GetMapping("/auth")
    public String auth(){
        String url =
        UrlUtils.getWxCodeUrl(wxPlatformConfig,redirectUrl,UrlUtils.snsapi_userinfo,"123");
        return "redirect:"+url;
    }
//第二步：通过code换取网页授权access_token
    //特殊的网页授权access_token,与基础支持中的access_token（该access_token用于调用其他接口）不同
    @RequestMapping("/login")
    public String login(String code, String state, RedirectAttributes redirectAttributes){
        System.out.println(code+"  "+state);
        System.out.println(accessTokenEntity.getAppid());
        String url = UrlUtils.getWebAccessTokenUrl(wxPlatformConfig,code);
        String s = HttpsUtil.httpsRequestToString(url, "GET", null);
        redirectAttributes.addFlashAttribute("webAccessToken",s);
        return "redirect:/wxUserinfo";
    }

//第三部获取用户信息
    @RequestMapping("/wxUserinfo")
    @ResponseBody
    public String getWxUserinfo(@ModelAttribute("webAccessToken")String webAccessToken){
        System.out.println("webAccessToken:"+webAccessToken);
        JSONObject jsonObject = JSON.parseObject(webAccessToken);
        String wxUserinfoUrl = UrlUtils.getWxUserinfoUrl(jsonObject.getString("access_token"), jsonObject.getString("openid"));
        String get = HttpsUtil.httpsRequestToString(wxUserinfoUrl, "GET", null);
        WxUserInfo wxUserInfo = JSON.parseObject(get, WxUserInfo.class);
        wxUserInfoHandler.handnler(wxUserInfo);
        return "SUCCESS";
    }


}
