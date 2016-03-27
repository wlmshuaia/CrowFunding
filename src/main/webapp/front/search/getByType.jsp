<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/6
  Time: 下午6:59
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
    <title>众梦空间</title>

    <%@ include file="../common/header-link.jsp" %>
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/common-front.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/index.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/header.jsp" %>

<div class="main m-con">
    <div class="one-obj">
        <ul class="am-avg-sm-2 am-thumbnails am-list" id="events-list">
            <div class="my-loader align-center">
                <i class="am-icon-spinner am-icon-spin"></i>
                <p>加载中...</p>
            </div>
        </ul>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>

<%@ include file="../common/footer-script.jsp" %>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/amazeui/js/amazeui.lazyload.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/layer/layer.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/search.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/amazeui/js/handlebars.min.js"></script>

<script>
    $(function () {
        var type = '<%=request.getParameter("type")%>';
        var labelid = '<%=request.getParameter("labelid")%>';
        var cateid = '<%=request.getParameter("cateid")%>';
        if(type != 'null' && type != undefined) {
            var aData = {'type': type};
            MyLoader.fLoad(aData, "front/Search/getByType.do", "#events-list");
        } else if(labelid != 'null') {
            var aData = {'labelid': labelid};
            MyLoader.fLoad(aData, "front/Search/searchByLabel.do", "#events-list");
        } else if(cateid != 'null') {
            var aData = {'cateid': cateid};
            MyLoader.fLoad(aData, "front/Search/searchByCate.do", "#events-list");
        }
    });
</script>

</html>
