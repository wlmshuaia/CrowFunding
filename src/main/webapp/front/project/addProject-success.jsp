<%@ page import="com.tide.utils.DataUtils" %><%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/10
  Time: 下午2:58
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

    <%@ include file="../common/header-link.jsp" %>

    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/common-front.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/add-project.css" rel="stylesheet">

    <title>发布项目成功</title>

</head>
<body>

    <div class="main align-center am-vertical-align">
        <div class="am-vertical-align-middle">
            <h2>提交审核</h2>
            <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/success.png" width=100 height=60 alt="">
            <div class="p-gray">
                <p>我们会尽快完成审核!</p>
                <p>请留意我们的微信通知哦~</p>
            </div>
            <a href="front/Index/index.do" class="btn-my a">去逛逛</a>
        </div>
    </div>

</body>
</html>
