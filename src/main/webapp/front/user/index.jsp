<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/10
  Time: 下午3:30
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

    <title>我</title>

    <%@ include file="../common/header-link.jsp"%>
    <!-- Custom styles for this template -->

    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/common-front.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/personal.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/designer.css" rel="stylesheet">

</head>
<body>

<%-- main 主要内容区 --%>
<div class="main">
    <%-- 头部导航 --%>
    <div class="personal-header">
        <ul class="am-avg-sm-3 am-thumbnails">
            <li><span class="f-active">我发布的</span></li>
            <li><span>我支持的</span></li>
            <li><span>我关注的</span></li>
        </ul>
    </div>

    <%-- 内容 --%>
    <div class="personal-content">
        <div class="p-obj" style="display: block;">
            <div class="am-btn-group p-header">
                <ul class="am-avg-sm-3 am-thumbnails">
                    <li class="s-active">全部</li>
                    <li>进行中</li>
                    <li>已达成</li>
                </ul>
            </div>
            <%-- 我发布的: 全部 --%>
            <div class="p-con-box" style="display: block;">
                <ul class="am-avg-sm-2 am-thumbnails">
                    <div class="my-loader align-center">
                        <i class="am-icon-spinner am-icon-spin"></i>
                        <p>加载中...</p>
                    </div>
                </ul>
            </div>
            <%-- 我发布的: 进行中 --%>
            <div class="p-con-box">
                <ul class="am-avg-sm-2 am-thumbnails"></ul>
            </div>
            <%-- 我发布的: 已达成 --%>
            <div class="p-con-box">
                <ul class="am-avg-sm-2 am-thumbnails"></ul>
            </div>
        </div>

        <div class="p-obj">
            <div class="am-btn-group p-header">
                <ul class="am-avg-sm-4 am-thumbnails">
                    <li class="s-active">全部</li>
                    <li>进行中</li>
                    <li>已达成</li>
                    <li>未达成</li>
                </ul>
            </div>
            <%-- 我支持的: 全部 --%>
            <div class="p-con-box" style="display: block;">
                <ul class="am-avg-sm-2 am-thumbnails"></ul>
            </div>
            <%-- 我支持的: 进行中 --%>
            <div class="p-con-box">
                <ul class="am-avg-sm-2 am-thumbnails"></ul>
            </div>
            <%-- 我支持的: 已达成 --%>
            <div class="p-con-box">
                <ul class="am-avg-sm-2 am-thumbnails"></ul>
            </div>
            <%-- 我支持的: 未达成 --%>
            <div class="p-con-box">
                <ul class="am-avg-sm-2 am-thumbnails"></ul>
            </div>
        </div>

        <div class="p-obj">
            <div class="am-btn-group t-header">
                <ul class="am-avg-sm-2 am-thumbnails">
                    <li class="t-active">项目</li>
                    <li>设计师</li>
                </ul>
            </div>

            <div class="am-cf"></div>

            <div class="p-obj-box" style="display: block;">
                <div class="am-btn-group p-header">
                    <ul class="am-avg-sm-3 am-thumbnails">
                        <li class="s-active">全部</li>
                        <li>进行中</li>
                        <li>已达成</li>
                    </ul>
                </div>
                <%-- 我关注的 - 项目 - 全部 --%>
                <div class="p-con-box" style="display: block;">
                    <ul class="am-avg-sm-2 am-thumbnails"></ul>
                </div>
                <%-- 我关注的 - 项目 - 进行中 --%>
                <div class="p-con-box">
                    <ul class="am-avg-sm-2 am-thumbnails"></ul>
                </div>
                <%-- 我关注的 - 项目 - 已达成 --%>
                <div class="p-con-box">
                    <ul class="am-avg-sm-2 am-thumbnails"></ul>
                </div>
            </div>

            <div class="p-obj-box">
                <%-- 我关注的 - 设计师 - 全部 --%>
                <div class="p-con-box" style="display: block;">
                    <ul class="am-avg-sm-2 am-thumbnails"></ul>
                </div>
            </div>

        </div>
    </div>

</div>

<%-- 底部栏 --%>
<%@ include file="../common/footer.jsp"%>

</body>
<%@ include file="../common/footer-script.jsp"%>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/amazeui/js/amazeui.lazyload.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<%--<script src="dist/js/front/common-handler.js"></script>--%>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/personal.js"></script>
<%--<script src="dist/js/front/personal.js"></script>--%>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/designer.js"></script>

</html>
