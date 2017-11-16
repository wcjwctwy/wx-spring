package cn.sf80.wx.check.controller;

import cn.sf80.wx.check.token.CheckoutUtil;
import cn.sf80.wx.check.token.MessageUtil;
import cn.sf80.wx.check.token.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);



    @GetMapping("/check")
    public String check(String signature,String timestamp,String nonce,String echostr){
        LOGGER.debug("signature:"+signature);
        LOGGER.debug("timestamp:"+timestamp);
        LOGGER.debug("nonce:"+nonce);
        LOGGER.debug("echostr:"+echostr);
        if (CheckoutUtil.checkSignature(signature, timestamp, nonce))
        {
            return echostr;
        }else {
            return null;
        }
    }

    @PostMapping("/check")
    public String check(HttpServletRequest request){
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            LOGGER.debug(parameterNames.nextElement());
        }
        Map<String, String> map = null;
        try {
            //将接收的消息转换成map
            map = MessageUtil.xmlToMap(request);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        String rtnMsg = null;
        //判断消息类型
        if("text".equals(msgType)) { //文本消息
            TextMessage textMsg = new TextMessage();
            textMsg.setToUserName(fromUserName);//从消息发送方在返回时变成消息接收方
            textMsg.setFromUserName(toUserName);
            textMsg.setMsgType(msgType);
            textMsg.setCreateTime(new Date().getTime());
            textMsg.setContent("您发送的消息是:" + content);
            //返回消息
            rtnMsg = MessageUtil.textMessageToXml(textMsg);
        }
        return rtnMsg;
    }

}
