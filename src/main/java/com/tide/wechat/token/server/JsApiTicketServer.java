package com.tide.wechat.token.server;

/**
 * Ticket Server 适配器
 * Created by wengliemiao on 16/3/3.
 */
public class JsApiTicketServer extends AbsServer implements TicketServer {
    @Override
    public String ticket() {
        return super.token();
    }

    @Override
    public IServer defaultServer() {
        return JsApiTicketMemServer.instance();
    }
}
