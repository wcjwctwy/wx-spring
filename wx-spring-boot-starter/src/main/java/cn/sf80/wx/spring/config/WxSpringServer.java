package cn.sf80.wx.spring.config;

import cn.sf80.wx.spring.pojo.WxPlatformConfig;

public class WxSpringServer {

    private WxPlatformConfig wxPlatformConfig;

    public WxPlatformConfig getWxPlatformConfig() {
        return wxPlatformConfig;
    }

    public void setWxPlatformConfig(WxPlatformConfig wxPlatformConfig) {
        this.wxPlatformConfig = wxPlatformConfig;
    }
}
