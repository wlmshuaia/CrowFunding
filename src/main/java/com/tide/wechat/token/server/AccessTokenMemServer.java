package com.tide.wechat.token.server;

import com.tide.wechat.token.AccessToken;

/**
 * 内存中控服务器
 * access_token 中控服务器
 * access_token 保存在内存中,过期则自动刷新
 * 此中控服务器采用单例模式，提供单一的访问点，并且持有全局唯一的accessToken对象
 *
 * Created by wengliemiao on 16/3/3.
 */
public class AccessTokenMemServer implements IServer {

    private static AccessTokenMemServer tokenServer = new AccessTokenMemServer();

    private AccessToken accessToken = new AccessToken();

    private int requestTimes = 1; // token 请求失败后重新请求的次数

    private AccessTokenMemServer() {
        refresh();
    }

    public static AccessTokenMemServer instance() {
        return tokenServer;
    }

    /**
     * 通过中控服务器获取token
     * @return
     */
    private AccessToken accessToken() {
        if(this.accessToken.isValid() == false) {
            refresh();
        }
        return this.accessToken();
    }

    /**
     * 通过中控服务器得到accessToken
     * @return
     */
    @Override
    public String token() {
        return accessToken.getToken();
    }

    /**
     * 服务器刷新 token
     */
    private void refresh() {
        for (int i = 0; i < requestTimes; i ++) {
            if(this.accessToken.requestNewToken() == true) {
                break;
            }
        }
    }
}
