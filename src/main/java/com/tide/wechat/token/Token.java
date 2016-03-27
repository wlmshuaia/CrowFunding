package com.tide.wechat.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tide.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wengliemiao on 16/3/3.
 */
public abstract class Token {
    private String token; // token
    private long expires; // token 有效时间

    private long tokenMadeTime; // token 产生时间
    private int redundance = 10 * 1000; // 冗余时间, 提前10秒去请求新的 token

    public String getToken() {
        return this.token;
    }

    public long getExpires() {
        return this.expires;
    }

    public boolean requestNewToken() {
        String url = accessTokenUrl();
        String result = HttpUtils.getResponseJson(url); // 获取 access_token
        if(StringUtils.isBlank(result)) {
            return false;
        }
        if(parseData(result) == false) {
            return false;
        }
        return true;
    }

    /**
     * 解析 token 数据
     * @param data
     * @return
     */
    private boolean parseData(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        String sTokenName = tokenName();
        String sExpiresInName = expiresInName();
        try {
            Map<String, Object> map = objectMapper.readValue(data, HashMap.class);
            if(!map.containsKey(sTokenName)) {
                return false;
            }
            String sToken = map.get(sTokenName).toString();
            if(StringUtils.isBlank(sToken)) {
                return false;
            }
            // 存储 token 和 token产生时间
            this.token = sToken;
            this.tokenMadeTime = new Date().getTime();

            String sExpiresIn = map.get(sExpiresInName).toString();
            if(StringUtils.isBlank(sExpiresIn)) {
                return false;
            } else {
                this.expires = Long.parseLong(sExpiresIn);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断 access_token 是否有效
     * @return
     */
    public boolean isValid() {
        if(StringUtils.isBlank(this.token)) {
            return false;
        }
        if(this.expires <= 0) {
            return false;
        }
        if(isExpire()) {
            return false;
        }
        return true;
    }

    /**
     * 是否过期
     * @return
     */
    private boolean isExpire() {
        Date currDate = new Date();
        long currTime = currDate.getTime();
        long expireTime = expires * 1000 - redundance;
        if((tokenMadeTime + expireTime) > currTime) {
            return false;
        }
        return true;
    }

    /**
     * token 的参数名称
     * @return
     */
    protected abstract String tokenName();

    /**
     * expireIn的参数名称
     * @return
     */
    protected abstract String expiresInName();

    /**
     * 组织access_token的请求url
     * @return
     */
    protected abstract String accessTokenUrl();
}
