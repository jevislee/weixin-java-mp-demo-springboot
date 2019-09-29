package com.github.binarywang.demo.wx.mp.controller;

import com.github.binarywang.demo.wx.mp.config.WxMpProperties;
import com.github.binarywang.demo.wx.mp.pojo.BaseResponse;
import com.github.binarywang.demo.wx.mp.pojo.ExtraParamResponse;
import com.github.binarywang.demo.wx.mp.pojo.UserInfo;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MyController {

    @Autowired
    private WxMpProperties properties;

    @GetMapping("/api/mobile/v1/index/tags")
    public String tags() throws IOException {
        return doHttpGet("http://cms.huodongshu.com/api/mobile/v1/index/tags");
    }

    @GetMapping("/api/mobile/v1/index/getUserInfo")
    public BaseResponse getUserInfo(@RequestParam String authCode) {
        String appID = properties.getConfigs().get(0).getAppId();
        String appsecret = properties.getConfigs().get(0).getSecret();

        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        String data = doHttpGet(accessTokenUrl.replace("APPID", appID).replace("SECRET", appsecret).replace("CODE", authCode));
        JSONObject jsonData = JSONObject.fromObject(data);
        if (jsonData.has("openid")) {
            String openId = jsonData.getString("openid");
            String accessToken = jsonData.getString("access_token");
            String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
            String userInfo = doHttpGet(userInfoUrl.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken));

            jsonData = JSONObject.fromObject(userInfo);
            UserInfo ret = new UserInfo();
            ret.accountId = 1;
            ret.openId = jsonData.getString("openid");
            ret.nickName = jsonData.getString("nickname");
            ret.headImg = jsonData.getString("headimgurl");
            return new ExtraParamResponse<>(ret);
        } else {
            return BaseResponse.loginFailed("openid not found");
        }
    }

    private String doHttpGet(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        String resultBody = null;
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            resultBody = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return resultBody;
    }
}
