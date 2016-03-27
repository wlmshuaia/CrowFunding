<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/13
  Time: 下午8:16
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

    <title>小伙伴们说(${cMapList.size()})</title>

    <%@ include file="../common/header-link.jsp" %>
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/common-front.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/comments.css" rel="stylesheet">
    <%--<link href="dist/css/front/comments.css" rel="stylesheet">--%>

</head>
<body>

<div class="main">

    <div class="one-con">
        <div class="con-header">
            <span class="am-icon-chevron-left am-align-left arrow-left p-gray" onclick="history.go(-1)"></span>

            <div class="align-center">小伙伴们说(${cMapList.size()})</div>
        </div>
        <div class="p-con">
            <ul class="am-comments-list am-comments-list-flip">

                <c:forEach items="${cMapList}" var="obj">
                    <li class="am-comment">

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

                        <div class="am-comment-main">
                            <header class="am-comment-hd">
                                <div class="am-comment-meta">
                                    <a href="javascript:void(0)" class="am-comment-author">${obj.user.nickname}</a>
                                </div>
                                <input type="hidden" name="commentid" value="${obj.comment.id}">
                                <div class="right zan-con">
                                    <div class="zan-num">${obj.comment.likenum}</div>
                                    <c:choose>
                                        <c:when test="${obj.ifZan == 1}">
                                            <div class="zan-success right" onclick="fToCancelZan(this)"></div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="zan right" onclick="fToZan(this)"></div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </header>
                            <div class="am-comment-bd">
                                ${obj.comment.content}
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <input type="hidden" name="projectid" value="${projectid}">

    <%-- 底部操作栏 --%>
    <div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default next-step" id="">
        <button type="submit" class="btn btn-default btn-my-submit" onclick="fToComment()">评论</button>
    </div>

</div>

</body>

<%@ include file="../common/footer-script.jsp" %>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/comments.js"></script>
<%--<script src="dist/js/front/comments.js"></script>--%>

<script>
    $(function() {

    });
</script>

</html>
