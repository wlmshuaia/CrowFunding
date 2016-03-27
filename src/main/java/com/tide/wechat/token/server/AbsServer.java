package com.tide.wechat.token.server;

/**
 * Created by wengliemiao on 16/3/3.
 */
public abstract class AbsServer implements IServer {

    @Override
    public String token() {
        return server().token();
    }

    public IServer server() {
        return defaultServer();
    }

    public abstract IServer defaultServer() ;
}
