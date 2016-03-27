<%--
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

    <!-- Bootstrap core CSS -->
    <%--<link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/css/fileinput.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <%--<link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/add-project.css" rel="stylesheet">--%>
    <link href="dist/css/front/add-project.css" rel="stylesheet">

    <%@ include file="../common/header-link.jsp"%>

</head>
<body>

<div class="add-project-form-con">
    <form action="" method="post" enctype="multipart/form-data" class="am-form" id="addProjectForm">
        <div class="am-form-group">
            <label for="title">作品名称</label>
            <span class="right gray">不多于22字</span>
            <input type="text" name="title" id="title" >
        </div>

        <div class="am-form-group">
            <label for="intro">作品简介</label>
            <span class="right gray">不多于54字</span>
            <textarea name="intro" id="intro" class="text-area" id="intro" cols="15" rows="5"></textarea>
        </div>
        <div class="am-form-group upload-file-ul-con">
            <label>图片展示</label>
            <ul class="am-avg-sm-3 am-thumbnails upload-file-ul">
                <li id="multipleChoose">
                    <%--<div class="file-img-con am-vertical-align" onclick="fToClickFile(this)">--%>
                    <div class="file-img-con am-vertical-align">
                        <img src="" alt="" class="img-preview">
                    </div>
                    <input type="file" name="projectimgsMultipile" class="file-img-uploader" onchange="fShowdImg(this)" multiple accept="image/*">
                </li>
            </ul>
        </div>
        <div class="am-form-group checkbox-con">
            <label>添加标签</label>
            <%--<span class="right gray">自定义标签</span>--%>
            <ul class="am-avg-sm-4 am-thumbnails">
                <c:forEach items="${labelList}" var="obj">
                    <li>
                        <label>
                            <div class="checkbox-pro-con">
                                <input type="checkbox" value="${obj.id}" name="label" />
                                <label></label>
                            </div>
                            <span class="checkbox-name">${obj.labelname}</span>
                        </label>

                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="am-form-group">
            <label>选择底衫</label>
            <div class="attr-choose">
                <label for="style">款式:</label>
                <select name="proid" onchange="fShowColorPrice(this)" class="form-control" id="style">
                    <option value="" selected>选择款式</option>
                    <c:forEach items="${productList}" var="obj">
                        <option value="${obj.id}">${obj.proname}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="attr-choose">
                <label for="color" class="">颜色:</label>
                <%--<select name="color" class="form-control">--%>
                    <%--<option value="" selected>选择颜色</option>--%>
                <%--</select>--%>
                <div class="choose-color" id="color">
                    <ul class="am-avg-sm-3 am-thumbnails" id="colorCon">
                        <li class="no-color">
                            请先选择款式
                        </li>
                        <%--<c:forEach items="${labelList}" var="obj">--%>
                            <%--<li>--%>
                                <%--<label>--%>
                                    <%--<div class="checkbox-pro-con">--%>
                                        <%--<input type="checkbox" value="${obj.id}" name="label" />--%>
                                        <%--<label></label>--%>
                                    <%--</div>--%>
                                    <%--<span class="checkbox-name">${obj.labelname}</span>--%>
                                <%--</label>--%>
                            <%--</li>--%>
                        <%--</c:forEach>--%>
                    </ul>
                </div>
            </div>

        </div>

        <div class="am-form-group">
            <label>价格</label>
            <span class="red" id="pro-price">0</span> 元
        </div>

        <input type="hidden" name="token" value="${token_add_project_1}">

        <div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default next-step">
            <img src="dist/imgs/icon_close.png" alt="" onclick="history.go(-1)">
            <button type="button" class="btn btn-default btn-my-submit" onclick="fNextStep()">下一步</button>
        </div>

        <div class="loading-con">
            <div class="loading-loader">
                <div class="loader">
                    <i class="am-icon-spinner am-icon-spin"></i>
                    上传中
                </div>
            </div>
        </div>

    </form>
</div>

<%@ include file="../common/footer-script.jsp"%>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<%-- 微信 jssdk --%>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/add-project.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/add-project-cloth.js"></script>
<%--<script src="dist/js/front/add-project.js"></script>--%>
<%--<script src="dist/js/front/add-project-cloth.js"></script>--%>

<script>
    $(function() {
        var url = "<%=request.getAttribute("url")%>";
        fWechatConfig(url);
    });
</script>


</body>
</html>
