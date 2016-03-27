package com.tide.wechat.token.server;

import com.tide.wechat.token.Ticket;
import com.tide.wechat.token.TicketType;

/**
 * Created by wengliemiao on 16/3/3.
 */
public class JsApiTicketMemServer implements IServer {

    private static JsApiTicketMemServer jsApiTicketMemServer = new JsApiTicketMemServer();

    private Ticket jsapiTicket = new Ticket(TicketType.jsapi);

    private int requestTimes = 1; // token请求失败后重新请求的次数

    public static JsApiTicketMemServer instance() {
        return jsApiTicketMemServer;
    }

    private JsApiTicketMemServer() {
        refresh();
    }

    private void refresh() {
        for (int i = 0; i < requestTimes; i ++) {
            if(this.jsapiTicket.requestNewToken() == true) {
                break;
            }
        }
    }

    @Override
    public String token() {
        if(this.jsapiTicket.isValid() == false) {
            refresh();
        }
        return this.jsapiTicket.getToken();
    }
}
