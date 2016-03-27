<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/20
  Time: 下午5:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
//    String basePath = "http://tide.oss-cn-hangzhou.aliyuncs.com";
%>
<html>
<head>
    <base href="<%=basePath%>">

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=false">

    <title>主题</title>

    <!-- Custom styles for this template -->
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/common-front.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/theme.css" rel="stylesheet">

    <%@ include file="../common/header-link.jsp" %>

</head>
<body>

<%-- main 主要内容区 --%>
<div class="main">

    <%-- 主题 --%>
    <div class="one-con">
        <div class="project-content">
            <ul class="am-avg-sm-1 am-thumbnails" id="theme-con">
                <div class="my-loader align-center">
                    <i class="am-icon-spinner am-icon-spin"></i>
                    <p>主题加载中...</p>
                </div>
            </ul>
        </div>
    </div>

</div>

<%-- 底部栏 --%>
<%@ include file="../common/footer.jsp"%>

</body>

<%@ include file="../common/footer-script.jsp" %>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/amazeui/js/amazeui.lazyload.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/theme.js"></script>

</html>
