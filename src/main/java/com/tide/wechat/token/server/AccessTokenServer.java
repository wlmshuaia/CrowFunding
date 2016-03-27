package com.tide.wechat.token.server;

/**
 * Created by wengliemiao on 16/3/3.
 */
public class AccessTokenServer extends AbsServer implements TokenServer {

    public String token(){
        return super.token();
    }

    @Override
    public IServer defaultServer() {
        return AccessTokenMemServer.instance();
    }
}
