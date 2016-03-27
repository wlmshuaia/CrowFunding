<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/16
  Time: 上午10:45
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

    <title>访问出错</title>

    <!-- Custom styles for this template -->
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/index.css" rel="stylesheet">

    <%@ include file="../common/header-link.jsp"%>

</head>
<body>

<%-- main 主要内容区 --%>
<div class="main am-vertical-align">
    <div class="am-vertical-align-middle align-center" style="width: 100%;">
        <h4>访问出错!<br>${errorMsg}</h4>
    </div>
</div>

<%-- 底部栏 --%>
<%@ include file="../common/footer.jsp"%>

</body>

<%@ include file="../common/footer-script.jsp"%>

</html>
