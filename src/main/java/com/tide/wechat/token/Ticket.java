package com.tide.wechat.token;

/**
 * Created by wengliemiao on 16/3/3.
 */
public class Ticket extends Token {
    private static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";

    private String type;

    public Ticket(TicketType ticketType) {
        super();
        this.type = ticketType.name();
    }

    @Override
    protected String tokenName() {
        return "ticket";
    }

    @Override
    protected String expiresInName() {
        return "expires_in";
    }

    @Override
    protected String accessTokenUrl() {
        String accessToken = TokenProxy.getAccessToken();
        return TICKET_URL + "access_token=" + accessToken + "&type=" + this.type;
    }
}
