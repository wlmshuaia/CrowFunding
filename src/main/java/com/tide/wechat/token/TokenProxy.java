package com.tide.wechat.token;

import com.tide.wechat.token.server.AccessTokenServer;
import com.tide.wechat.token.server.JsApiTicketServer;
import com.tide.wechat.token.server.TicketServer;
import com.tide.wechat.token.server.TokenServer;

/**
 * AccessToken 代理, 所有获取 accessToken 的地方都通过此代理获得
 * Created by wengliemiao on 16/3/3.
 */
public class TokenProxy {

    /**
     * 通过代理获取 access_token
     * @return
     */
    public static String getAccessToken() {
        TokenServer accessTokenServer = new AccessTokenServer();
        return accessTokenServer.token();
    }

    /**
     * 通过代理获取 jsapi_ticket
     * @return
     */
    public static String getJsApiTicket() {
        TicketServer jsapiTicketServer = new JsApiTicketServer();
        return jsapiTicketServer.ticket();
    }
}
