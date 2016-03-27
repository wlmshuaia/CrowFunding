<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/20
  Time: 下午5:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.tide.utils.DataUtils" %>
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
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=false">

    <title>众梦空间</title>

    <!-- Custom styles for this template -->
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/index.css" rel="stylesheet">
    <%--<link href="dist/css/front/index.css" rel="stylesheet">--%>

    <%@ include file="./front/common/header-link.jsp"%>
</head>
<body>

    <%-- 顶部栏 --%>
    <%@ include file="./front/common/header.jsp"%>

    <%-- 轮播图 --%>
    <div class="am-slider am-slider-default" data-am-flexslider id="index-slider">
        <ul class="am-slides" id="slide-con-index">
            <div class="my-loader slide align-center">
                <i class="am-icon-spinner am-icon-spin"></i>
                <p>轮播图加载中...</p>
            </div>
        </ul>
    </div>

    <%-- main 主要内容区 --%>
    <div class="main">
        <%-- 主题 --%>
        <div class="one-con am-scrollable-horizontal">
            <div class="con-header" id="theme-con-header-index"></div>
            <div class="theme-content">
                <table class="am-table am-table-striped am-text-nowrap">
                    <tbody>
                        <tr id="theme-con-index">
                            <div class="my-loader align-center" id="slider-theme">
                                <i class="am-icon-spinner am-icon-spin"></i>
                                <p>主题加载中...</p>
                            </div>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <hr data-am-widget="divider" class="am-divider am-divider-default my-divider" />

        <%-- 逛一逛 --%>
        <div class="one-con">
            <div class="con-header">
                <span><h4>逛一逛</h4></span>
            </div>

            <div class="project-content">
                <ul class="am-avg-sm-2 am-thumbnails" id="project-con-index">
                    <div class="my-loader project align-center">
                        <i class="am-icon-spinner am-icon-spin"></i>
                        <p>项目加载中...</p>
                    </div>
                </ul>
            </div>
        </div>

    </div>

    <%-- 底部栏 --%>
    <%@ include file="./front/common/footer.jsp"%>

</body>

<%@ include file="./front/common/footer-script.jsp"%>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/amazeui/js/amazeui.lazyload.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/index.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/search.js"></script>
<%--<script src="dist/js/front/search.js"></script>--%>
<script>
    $(function() {
        $("img.lazy").lazyload({
            effect : 'fadeIn',
        });
    });
</script>

</html>
