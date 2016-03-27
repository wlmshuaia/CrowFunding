package com.tide.controller.front;

import com.github.pagehelper.PageHelper;
import com.tide.bean.Project;
import com.tide.bean.User;
import com.tide.service.PageService;
import com.tide.service.ProjectService;
import com.tide.service.ThemeService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import com.tide.utils.HttpUtils;
import com.tide.wechat.WxPayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/21.
 */
@Controller
@RequestMapping(value = "/front/Index")
//@SessionAttributes({"userid","username"})
public class FIndexAction {

    @Autowired
    private PageService pageService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    /**
     * 微信授权获取用户信息:微信授权登录
     * @param code
     * @return
     */
    @RequestMapping(value = "oauth2")
    public Object oauth2(String code, HttpSession session, Model model) {
        String errorMsg = "";
        WxPayApi wxPayApi = new WxPayApi();

        // 1.2获取 access_token, refresh_token
        Map<String, Object> accessTokenMap = wxPayApi.getAccessToken(code);

        if(accessTokenMap.containsKey("errcode")) { // 是否出错
            errorMsg = "获取用户信息refresh_token出错, errcode: "+accessTokenMap.get("errcode") + ", errmsg: "+accessTokenMap.get("errmsg");
        }

        Object oRefresh = accessTokenMap.get("refresh_token");
        if(oRefresh != null || accessTokenMap.containsKey("access_token")) {
            Object oAccessToken = null;
            Object oOpenid = null;

            if(oRefresh != null) {
                // 2.1刷新 access_token
                Map<String, Object> refreshTokenMap = wxPayApi.getRefreshToken(oRefresh.toString());

                if(refreshTokenMap.containsKey("errcode")) { // 是否出错
                    errorMsg = "获取用户信息access_token出错, errcode: "+refreshTokenMap.get("errcode") + ", errmsg: "+refreshTokenMap.get("errmsg");
                } else {
                    oAccessToken = refreshTokenMap.get("access_token");
                    oOpenid = refreshTokenMap.get("openid");
                }

            } else {
                // 2.2直接使用 access_token
                oAccessToken = accessTokenMap.get("access_token");
                oOpenid = accessTokenMap.get("openid");
            }

            if(oAccessToken != null && oOpenid != null) {
                // 3.获取用户信息
                Map<String, Object> userMap = wxPayApi.getUserInfo(oAccessToken.toString(), oOpenid.toString());

                if(userMap.containsKey("errcode")) { // 是否出错
                    errorMsg = "获取用户信息openid出错, errcode: "+userMap.get("errcode") + ", errmsg: "+userMap.get("errmsg");
                }

                // 4.将用户数据保存到数据库
                if(userMap.containsKey("openid")) {
                    String openid = userMap.get("openid").toString();
                    Integer userid = 0;
                    String username = "";
                    User u = this.userService.getUserByOpenid(openid);
                    if(u == null) { // 数据库无数据则插入该条数据
                        User user = new User();

                        user.setOpenid(openid);

                        if(userMap.containsKey("nickname")) {
                            String nickname = userMap.get("nickname").toString();
                            user.setNickname(HttpUtils.filterEmoji(nickname));
                        }
                        if(userMap.containsKey("sex")) {
                            user.setSex(Integer.parseInt(userMap.get("sex").toString()));
                        }
                        if(userMap.containsKey("headimgurl")) {
                            user.setHeadimgurl(userMap.get("headimgurl").toString());
                        }
                        if(userMap.containsKey("province")) {
                            user.setProvince(userMap.get("province").toString());
                        }
                        if(userMap.containsKey("city")) {
                            user.setCity(userMap.get("city").toString());
                        }
                        if(userMap.containsKey("language")) {
                            user.setLanguage(userMap.get("language").toString());
                        }
                        user.setType(0);
                        this.userService.insert(user);

                        userid = user.getId();
                        username = user.getNickname();
                    } else {
                        userid = u.getId();
                        username = u.getNickname();
                    }

                    // 设置信息到session
                    session.setAttribute("userid", userid);
                    session.setAttribute("username", username);
//                    model.addAttribute("userid", userid);
//                    model.addAttribute("username", username);

                    if(session.getAttribute("fromUrl") != null) {
                        String fromUrl = session.getAttribute("fromUrl").toString();
                        session.removeAttribute("fromUrl");
                        return "redirect:" + fromUrl;
                    }
                    return "redirect:index.do";
                }
            }
        }

        model.addAttribute("errorMsg", errorMsg);
        return "front/error/error";
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    /**
     * 获取首页轮播图
     * @return
     */
    @RequestMapping(value = "/getIndexSlide")
    @ResponseBody
    public Object getIndexSlide() {
        return this.pageService.selectAllDisplay();
    }

    /**
     * 获取首页主题
     * @return
     */
    @RequestMapping(value = "/getIndexTheme")
    @ResponseBody
    public Object getIndexTheme(HttpSession session) {
        if(session.getAttribute("userid") == null) {
            session.setAttribute("userid", "1");
            session.setAttribute("username", "wlm");
        }
        return this.themeService.getThemeMapList(session);
    }

    /**
     * 获取首页逛一逛项目
     * @return
     */
    @RequestMapping(value = "/getIndexProject")
    @ResponseBody
    public Object getIndexProject(@RequestBody Map<String, String> map, HttpSession session) {
        if(session.getAttribute("userid") == null) {
            session.setAttribute("userid", "1");
            session.setAttribute("username", "wlm");
        }

        Integer start = Integer.parseInt(map.get("start"));

        PageHelper.startPage(start, DataUtils.INRO_PROJECT_NUM);
        List<Project> pList = this.projectService.getProjectByStatus(DataUtils.I_PROJECT_CROWFUNDING);
        List<Map<String, Object>> projectList = new ArrayList<>();

        if(pList != null) {
            for (Project p : pList){
                Map<String, Object> pMap = this.projectService.getProjectInfo(p, session);
                projectList.add(pMap);
            }
        }
        return projectList;
    }
}
