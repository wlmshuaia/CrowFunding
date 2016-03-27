<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/29
  Time: 下午3:37
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

    <title>个人中心</title>

    <!-- 公共样式 -->
    <%@ include file="../common/header-link.jsp" %>

    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/personal.css" rel="stylesheet">

</head>
<body>
<%@ include file="../common/header.jsp" %>

<div class="admin-main">
    <div class="container-fluid">
        <%@ include file="../common/nav.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h2 class="page-header">个人中心</h2>


            <div class="col-md-6">

                <div class="panel panel-default one-obj">
                    <div class="panel-heading">
                        基本信息
                        <%--<a href="javascript:void(0)" class="btn btn-my">修改个人信息</a>--%>
                        <%--<div class="btn-toolbar" role="toolbar">--%>
                            <%--<div class="btn-group" role="group">--%>
                                <%--<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="tool-bar pull-right">
                            <button type="button" class="btn btn-default btn-xs" aria-label="Left Align">
                                <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                修改个人信息
                            </button>
                        </div>
                    </div>
                    <div class="panel-body">
                        <ul>
                            <li>用户名: ${admin.adminname}</li>
                            <li>角色: 暂无</li>
                            <li>权限: 暂无</li>
                            <li>上次登陆IP: ${admin.lastloginip}</li>
                            <li>上次登陆时间: ${admin.lastlogintime}</li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/handler-order.js"></script>

</body>
</html>

