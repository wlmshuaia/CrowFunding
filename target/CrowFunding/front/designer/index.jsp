<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/9
  Time: 下午3:56
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
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=false">

    <title>设计师</title>

    <%@ include file="../common/header-link.jsp"%>

    <!-- Custom styles for this template -->
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/common-front.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/designer.css" rel="stylesheet">

</head>
<body>

<%-- main 主要内容区 --%>
<div class="main">
    <div class="my-loader align-center">
        <i class="am-icon-spinner am-icon-spin"></i>
        <p>设计师加载中...</p>
    </div>
</div>

<%-- 底部栏 --%>
<%@ include file="../common/footer.jsp"%>
</body>

<%@ include file="../common/footer-script.jsp"%>

<script src="<%=DataUtils.DOMAIN_FILE%>/assets/amazeui/js/amazeui.lazyload.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/designer.js"></script>
<script>
    $(function () {
        MyLoader.fLoad({'num': 4}, "front/Designer/getDesignerList.do", ".main", fGetDesignerHtml);
    });
</script>

</html>
