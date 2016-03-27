package com.tide.wechat.token;

import com.tide.utils.PropertiesUtils;

/**
 * Created by wengliemiao on 16/3/3.
 */
public class AccessToken extends Token {

    private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    @Override
    protected String tokenName() {
        return "access_token";
    }

    @Override
    protected String expiresInName() {
        return "expires_in";
    }

    @Override
    protected String accessTokenUrl() {
        String appid = PropertiesUtils.instance().readValue("APPID");
        String secret = PropertiesUtils.instance().readValue("SECRET");
        return ACCESS_TOKEN_URL + "&appid=" + appid + "&secret=" + secret;
    }
}
