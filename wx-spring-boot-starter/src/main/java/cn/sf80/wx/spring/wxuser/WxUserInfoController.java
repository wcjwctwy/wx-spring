package cn.sf80.wx.spring.wxuser;

import cn.sf80.wx.spring.WxUserInfoHandler;
import cn.sf80.wx.spring.config.WxSpringServer;
import cn.sf80.wx.spring.pojo.WxUserInfo;
import cn.sf80.wx.spring.utils.HttpsUtil;
import cn.sf80.wx.spring.utils.UrlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@Configuration
@ConditionalOnBean(WxUserInfoHandler.class)
public class WxUserInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxUserInfoController.class);


    @Autowired
    private WxSpringServer wxSpringServer;


    @Autowired
    private WxUserInfoHandler wxUserInfoHandler;

    @Value("${wx.login.redirectUrl}")
    private String redirectUrl;

    @Value("${wx.context.path}")
    private String wxContextPath;

//第一步：用户同意授权，获取code
    @GetMapping("${wx.context.path}/auth")
    public String auth(){
        String url =
        UrlUtils.getWxCodeUrl(wxSpringServer.getWxPlatformConfig(),redirectUrl,UrlUtils.snsapi_userinfo,"123");
        return "redirect:"+url;
    }
//第二步：通过code换取网页授权access_token
    //特殊的网页授权access_token,与基础支持中的access_token（该access_token用于调用其他接口）不同
    @RequestMapping("${wx.context.path}/wxredirecturl")
    public String login(String code, String state, RedirectAttributes redirectAttributes){
        LOGGER.debug("code:"+code+"  state:"+state);
        String url = UrlUtils.getWebAccessTokenUrl(wxSpringServer.getWxPlatformConfig(),code);
        String s = HttpsUtil.httpsRequestToString(url, "GET", null);
        redirectAttributes.addFlashAttribute("webAccessToken",s);
        return "redirect:"+wxContextPath+"/wxUserinfo";
    }

//第三部获取用户信息
    @RequestMapping("${wx.context.path}/wxUserinfo")
    public String getWxUserinfo(@ModelAttribute("webAccessToken")String webAccessToken){

        System.out.println("webAccessToken:"+webAccessToken);
        JSONObject jsonObject = JSON.parseObject(webAccessToken);
        Integer errcode = jsonObject.getInteger("errcode");
        if(!StringUtils.isEmpty(errcode)&&errcode.equals(40163)){
            LOGGER.debug("近期已经登陆过了");
        }else {
            String wxUserinfoUrl = UrlUtils.getWxUserinfoUrl(jsonObject.getString("access_token"), jsonObject.getString("openid"));
            String get = HttpsUtil.httpsRequestToString(wxUserinfoUrl, "GET", null);
            WxUserInfo wxUserInfo = JSON.parseObject(get, WxUserInfo.class);
            wxUserInfo.setRefresh_token(jsonObject.getString("refresh_token"));
            wxUserInfoHandler.handnler(wxUserInfo);
        }
        return "redirect:"+wxSpringServer.getWxPlatformConfig().getAuthSuccessUrl();
    }


}
