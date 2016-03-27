<%@ page import="com.tide.utils.DataUtils" %><%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/11/30
  Time: 下午2:52
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

    <title>发布项目</title>

    <%--<link href="dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/add-project.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/assets/rangemove/jquery.range.css" rel="stylesheet">
    <%--<link href="dist/css/front/add-project.css" rel="stylesheet">--%>
    <%--<link href="assets/rangemove/jquery.range.css" rel="stylesheet">--%>

    <%@ include file="../common/header-link.jsp"%>
</head>
<body>

<div class="add-project-form-con second">
    <form action="" method="post" class="am-form">
        <div class="form-group targetnum-container">
            <label for="title" class="add-seconnd-title">目标</label>
            <div class="targetnum-con">
               <input type="number" class="form-control targetnum" name="targetnum" id="title">
            </div>
            <c:choose>
                <c:when test="${param.fundtype != null && param.fundtype == 1}">
                    <span class="am-block add-seconnd-title type-tip">人</span>
                    <span class="am-block gray">不少于 <span>${param.leastnum}</span> 人</span>
                </c:when>
                <c:when test="${param.fundtype != null && param.fundtype == 2}">
                    <span class="am-block add-seconnd-title type-tip">元</span>
                    <span class="am-block gray">不少于 <span>${param.leastnum}</span> 元</span>
                </c:when>
                <c:otherwise>
                    <span class="am-block add-seconnd-title type-tip">件</span>
                    <span class="am-block gray">不少于 <span>${param.leastnum}</span> 件</span>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="form-group enddate-container">
            <label for="targetnum" class="add-seconnd-title">设定结束日期</label>
            <span class="tip-gray">请左右拖动下方圆点</span>
            <%--<input type="range" min="1" max="60" value="0" name="enddaterange" id="targetnum" class="range" onchange="fShowDate(this)">--%>
            <%--<div class="tip-enddate tip-gray">--%>
                <%--${param.releasedate}--%>
                <%--&lt;%&ndash;2016-01-26 14:41:00&ndash;%&gt;--%>
            <%--</div>--%>

            <div class="range-con" id="targetnum">
                <input type="hidden" name="enddaterange" class="single-slider" value="0" />
            </div>

            <div class="time-enddate">
                <select class="form-control" name="time-enddate" onchange="fChangeHour(this)">
                    <c:forEach begin="0" end="23" var="i">
                        <c:choose>
                            <c:when test="${i == param.hour}">
                            <%--<c:when test="${i == 14}">--%>
                                <option value="${i}" selected>${i}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${i}">${i}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                :
                <select class="form-control" name="minute-enddate" onchange="fChangeMinute(this)">
                    <c:forEach begin="0" end="59" var="i">
                        <c:choose>
                            <c:when test="${i == param.minute}">
                            <%--<c:when test="${i == 41}">--%>
                                <option value="${i}" selected>${i}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${i}">${i}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                :00
            </div>

            <div class="info-enddate">
                项目将于
                <span class="h2 red" id="endNum">0</span>天
                <span class="h3 red" id="endHour">0</span>时
                <span class="h3 red" id="endMinute">0</span>分
                后筹集结束
            </div>
        </div>
        <input type="hidden" name="projectid" value="${param.projectid}">
        <%--<input type="hidden" name="projectid" value="44">--%>
        <input type="hidden" name="releasedate" value="${param.releasedate}">
        <input type="hidden" name="enddate" value="${param.releasedate}">
        <%--<input type="hidden" name="releasedate" value="2016-01-26 14:41:00">--%>
        <%--<c:choose>--%>
            <%--<c:when test="${param.enddate != null}">--%>
                <%--<input type="hidden" name="enddate" value="${param.enddate}">--%>
            <%--</c:when>--%>
            <%--<c:otherwise>--%>
                <%--<input type="hidden" name="enddate" value="${param.releasedate}">--%>
            <%--</c:otherwise>--%>
        <%--</c:choose>--%>

        <%--<input type="hidden" name="enddate" value="2015-12-13 23:39:26">--%>
        <input type="hidden" name="leastnum" value="${param.leastnum}">
        <input type="hidden" name="token" value="${token_add_project_2}">
        <%--<button type="button" class="btn btn-default" onclick="fSubmit()">发布</button>--%>

        <div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default next-step">
            <img src="dist/imgs/icon_close.png" alt="" onclick="history.go(-1)">
            <button type="button" class="btn btn-default btn-my-submit" onclick="fSubmit()">发布设计</button>
        </div>

    </form>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<%--<script src="dist/js/jquery-2.1.1.min.js"></script>--%>
<%--<script src="dist/js/bootstrap.min.js"></script>--%>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="<%=DataUtils.DOMAIN_FILE%>/assets/layer/layer.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>

<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/add-project-second.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/rangemove/jquery.range.js"></script>
<%--<script src="dist/js/front/add-project-second.js"></script>--%>
<%--<script src="assets/rangemove/jquery.range.js"></script>--%>

</body>
</html>
