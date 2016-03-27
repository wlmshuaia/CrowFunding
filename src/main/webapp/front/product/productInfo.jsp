<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/21
  Time: 下午8:26
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

    <title>商品详情</title>

    <%@ include file="../common/header-link.jsp"%>

    <!-- Custom styles for this template -->
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/product-info.css" rel="stylesheet">

</head>
<body>
<div class="main">
    <div class="product-header">
        <span class="am-icon-chevron-left am-align-left arrow-left p-gray" onclick="history.go(-1)"></span>
        <c:choose>
            <c:when test="${isCloth != '0'}">
                <ul>
                    <li class="active" id="h-detail">详图</li>
                    <li id="h-care">洗护</li>
                    <li id="h-sizetables">尺码</li>
                </ul>
            </c:when>
            <c:otherwise>
                <%--<div>--%>
                    <%--关于项目--%>
                <%--</div>--%>
                <ul>
                    <li class="active" id="h-detail">关于项目</li>
                    <li id="h-care">说明</li>
                </ul>
            </c:otherwise>
        </c:choose>

    </div>
    <input type="hidden" name="productid" value="${pdList.get(0).productid}">
    <div class="product-info">
        <div id="detailimg" class="p-obj">
            <c:forEach items="${pdList}" var="obj">
                <img src="<%=DataUtils.DOMAIN_FILE%>/${obj.proimgurl}" alt="">
                <%--<img data-original="${obj.proimgurl}" alt="" class="lazy">--%>
            </c:forEach>
        </div>

        <div id="care" class="p-obj"></div>

        <div id="sizetables" class="p-obj"></div>
    </div>
</div>

</body>

<%@ include file="../common/footer-script.jsp"%>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/amazeui/js/amazeui.lazyload.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/productInfo.js"></script>

</html>
