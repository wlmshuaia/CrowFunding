<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/15
  Time: 下午3:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>用户授权</title>

</head>
<body>
    <%
        // 1.获取 code
        Object obj = request.getParameter("code");
        String sGetAccessTokenUrl = "";
        if(obj != null) {
            out.println("code: "+obj.toString());

            String APPID = "wx764d16c3043fedcb";
            String SECRET = "2f531514127d116333540269880233f8";

            String sCode = obj.toString();
            sGetAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
            sGetAccessTokenUrl = sGetAccessTokenUrl.replace("APPID", APPID);
            sGetAccessTokenUrl = sGetAccessTokenUrl.replace("SECRET", SECRET);
            sGetAccessTokenUrl = sGetAccessTokenUrl.replace("CODE", sCode);

            out.println("getAccessToken: "+sGetAccessTokenUrl);
        } else {
            System.out.println("code is null...");
            out.println("code is null...");
        }
    %>
</body>

    <script src="dist/js/jquery-2.1.1.min.js"></script>
    <script>
        $(function() {
            // 2.获取 access_token
            var sGetAccessUrl = "<%=sGetAccessTokenUrl%>";
//            var oData = fToAccess(sGetAccessUrl);
//            var sAccessToken = oData.access_token;
//            var sRefreshToken = oData.refresh_token;
//
//            // 3.刷新 access_token
//            var sRefreshUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
//            sRefreshUrl = sRefreshUrl.replace("APPID", "wx764d16c3043fedcb");
//            sRefreshUrl = sRefreshUrl.replace("REFRESH_TOKEN", sRefreshToken);
//            var oOpenid = fToAccess(sRefreshUrl);
//            var sRefreshAccessToken = oOpenid.access_token; // 刷新后的 access_token
//            var sOpenid = oOpenid.openid; // openid
//
//            // 4.获取用户信息
//            var sGetUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
//            sGetUserInfoUrl = sGetAccessUrl.replace("ACCESS_TOKEN", sRefreshAccessToken);
//            sGetUserInfoUrl = sGetAccessUrl.replace("OPENID", sOpenid);
//            var oUser = fToAccess(sGetAccessUrl);
//            console.log("openid: "+oUser.openid);
//            console.log("nickname: "+oUser.nickname);
//            console.log("headimgurl: "+oUser.headimgurl);

        });

        /**
         * 访问地址,返回数据
         * @param url
         * @returns {*}
         */
        function fToAccess(url) {
            var oRes = null;
            $.ajax({
                url: url,
                method: 'get',
                async: false,
                success: function(data) {
                    oRes = data;
                },
                error: function(data) {
                    console.log("fToAccess error...");
                }
            });
            return oRes;
        }
    </script>

</html>
