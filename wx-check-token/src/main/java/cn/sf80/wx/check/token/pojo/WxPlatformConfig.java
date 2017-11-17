package cn.sf80.wx.check.token.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


public class WxPlatformConfig {
    private String appID;
    private String appsecret;
    private String accessToken;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "WxPlatformConfig{" +
                "appID='" + appID + '\'' +
                ", appsecret='" + appsecret + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
