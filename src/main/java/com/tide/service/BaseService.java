package com.tide.service;

import javax.servlet.http.HttpSession;

/**
 * Created by wengliemiao on 16/1/3.
 */
public interface BaseService {
    int delete(Integer id, HttpSession session);
    int getCount();
}
