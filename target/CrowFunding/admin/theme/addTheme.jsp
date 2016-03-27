<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/17
  Time: 下午2:47
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

    <title>添加主题</title>

    <!-- 公共样式 -->
    <%@ include file="../common/header-link.jsp" %>
    
    <link href="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/css/fileinput.min.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/add-product.css" rel="stylesheet">

</head>
<body>
<%@ include file="../common/header.jsp" %>

<div class="admin-main">
    <div class="container-fluid">
        <%@ include file="../common/nav.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <ol class="breadcrumb">
                <li>主题管理</li>
                <li>添加主题</li>
            </ol>

            <div class="content">
                <form action="admin/Theme/addThemeHandle.do" method="post" class="form-horizontal" enctype="multipart/form-data">
                    <div class="one-con">
                        <div class="one-header">
                            <span>基本信息</span>
                        </div>
                        <div class="one-content">
                            <div class="form-group">
                                <label for="proname" class="control-label"><span class="red">*</span>主题名称</label>
                                <div class="col-md-4">
                                    <input type="text" name="name" class="form-control" id="proname" placeholder="名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label"><span class="red">*</span>主题海报</label>
                                <div class="col-md-4 file-img-con">
                                    <div id="kv-avatar-errors" class="center-block" style="width:800px;display:none"></div>
                                    <div class="kv-avatar center-block" style="width:200px">
                                        <input id="post" name="post" type="file" class="file-loading">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group select">
                                <label class="control-label"><span class="red">*</span>主题标签</label>
                                <div class="col-md-5 checkbox-con">

                                    <c:forEach items="${labelList}" var="obj">
                                        <label class="checkbox-inline">
                                            <input type="checkbox" name="label" value="${obj.id}"> ${obj.labelname}
                                        </label>
                                    </c:forEach>

                                </div>
                            </div>
                            <div class="form-group select">
                                <label class="control-label"><span class="red">*</span>是否显示</label>
                                <div class="col-md-8">
                                    <label class="radio-inline">
                                        <input type="radio" name="status" value="0" checked> 关闭
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="status" value="1"> 显示
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>

                    <button class="btn-my center" type="submit">
                        确认添加
                    </button>

                </form>
            </div>

        </div>

    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<script src="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/js/fileinput.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/js/fileinput_locale_zh.js"></script>

<script>
    /**
     * 海报图片上传
     */
    $("#post").fileinput({
        overwriteInitial: true,
        showClose: false,
        showCaption: false,
        browseLabel: '',
        removeLabel: '',
        browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
        removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
        removeTitle: 'Cancel or reset changes',
        elErrorContainer: '#kv-avatar-errors',
        msgErrorClass: 'alert alert-block alert-danger',
        defaultPreviewContent: '<img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/preview.jpg" alt="Your Avatar" style="width:160px">',
        layoutTemplates: {main2: '{preview} {remove} {browse}'},
        allowedFileExtensions: ["jpg", "png", "gif"]
    });
</script>

</body>
</html>

