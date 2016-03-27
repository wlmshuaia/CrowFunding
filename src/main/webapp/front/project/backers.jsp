<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/13
  Time: 下午8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <title>支持ta的人(${pbMapList.size()})</title>

    <%@ include file="../common/header-link.jsp" %>
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/common-front.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/backers.css" rel="stylesheet">

</head>
<body>

<div class="main">

    <div class="one-con">
        <div class="con-header">
            <span class="am-icon-chevron-left am-align-left arrow-left p-gray" onclick="history.go(-1)"></span>
            <div class="align-center">支持ta的人(${pbMapList.size()})</div>
        </div>
        <div class="p-con">
            <ul class="am-comments-list am-comments-list-flip">
                <c:forEach items="${pbMapList}" var="obj">
                    <li class="am-comment">
                        <span class="time">
                            <time>${obj.pb.backdate}</time>
                            <%--<fmt:parseDate value="${obj.pb.backdate}" var="parsedEmpDate"--%>
                                           <%--pattern="dd-MM-yyyy" />--%>
                                <%--<fmt:formatDate pattern="yyyy-MM-dd"--%>
                                                <%--value="${obj.pb.backdate}" />--%>
                            <%--2015-12-05--%>
                        </span>
                        <div class="content">
                            <span class="square"></span>
                            <c:choose>
                                <c:when test="${obj.user.headimgurl.indexOf('http') > -1}">
                                    <img src="${obj.user.headimgurl}" alt="" class="am-comment-avatar">
                                </c:when>
                                <c:when test="${obj.user.headimgurl.indexOf('http') == -1}">
                                    <img src="<%=DataUtils.DOMAIN_FILE%>/${obj.user.headimgurl}" alt="" class="am-comment-avatar">
                                </c:when>
                                <c:otherwise>
                                    <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/avatar.png" alt="" class="am-comment-avatar">
                                </c:otherwise>
                            </c:choose>
                            <a href="#link-to-user" class="am-comment-author">${obj.user.nickname}</a>
                            <span>支持了ta <span>${obj.pb.num}</span> 件衣服</span>
                        </div>

                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

</div>

</body>

<%@ include file="../common/footer-script.jsp" %>

</html>

