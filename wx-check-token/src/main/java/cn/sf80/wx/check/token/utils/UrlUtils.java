package cn.sf80.wx.check.token.utils;


import cn.sf80.wx.check.token.pojo.WxPlatformConfig;

public class UrlUtils {
    /*
    应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
     */
    public final static String snsapi_userinfo = "snsapi_userinfo";
    public final static String snsapi_base  = "snsapi_base";
    public static String getWxCodeUrl(WxPlatformConfig platformConfig, String redirectUrl, String scope, String redirectParam) {
        StringBuilder url = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?");
        url.append("appid=").append(platformConfig.getAppID())
                .append("&redirect_uri=").append(redirectUrl)
                .append("&response_type=code")
                .append("&scope=").append(scope)
                .append("&state=").append(redirectParam)
                .append("#wechat_redirect");
        return url.toString();

    }

    /*
    https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code
     */
    public static String getWebAccessTokenUrl(WxPlatformConfig platformConfig,String code){
        StringBuilder get_web_access_token = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?");
        get_web_access_token.append("appid=").append(platformConfig.getAppID())
                .append("&secret=").append(platformConfig.getAppsecret())
                .append("&code=").append(code)
                .append("&grant_type=authorization_code");
        return get_web_access_token.toString();
    }
    //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN

    public static String getWxUserinfoUrl(String access_token,String openid){
        StringBuilder get_user_info = new StringBuilder("https://api.weixin.qq.com/sns/userinfo?");
        get_user_info.append("access_token=").append(access_token)
                .append("&openid=").append(openid);
        return get_user_info.toString();
    }
}


