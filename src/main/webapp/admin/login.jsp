<%@ page import="com.tide.utils.DataUtils" %><%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/11/28
  Time: 下午5:20
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
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <%--<link rel="icon" href="../favicon.ico">--%>

    <title>众筹后台登录</title>

    <!-- 公共样式 -->
    <%@ include file="./common/header-link.jsp" %>

    <!-- Custom styles for this template -->
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/signin.css" rel="stylesheet">

</head>

<body>

<div class="container">

    <div class="login-container">
        <form class="form-signin" action="admin/Login/login.do" method="post">
            <h2 class="form-signin-heading siyuan-light h2 center">众筹后台管理系统</h2>

            <div class="form-group">
                <label for="inputName">账号:</label>
                <input type="text" name="adminname" id="inputName" class="form-control" required autofocus>
            </div>

            <div class="form-group">
                <label for="inputPassword">密码:</label>
                <input type="password" name="password" id="inputPassword" class="form-control" required>
            </div>

            <div class="checkbox">
                <label>
                    <input type="checkbox" value="remember-me"> 记住我一个星期
                </label>
            </div>
            <div class="btn-login">
                <button class="btn" type="submit">登录</button>
            </div>
        </form>
    </div>

</div> <!-- /container -->

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<%--<script src="<%=DataUtils.DOMAIN_FILE%>/assets/js/ie10-viewport-bug-workaround.js"></script>--%>
</body>
</html>

