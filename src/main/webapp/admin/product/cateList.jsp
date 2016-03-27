<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/10
  Time: 上午9:58
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

    <title>分类列表</title>

    <!-- 公共样式 -->
    <%@ include file="../common/header-link.jsp" %>

    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/add-product.css" rel="stylesheet">

</head>
<body>
<%@ include file="../common/header.jsp" %>

<!-- 模态框 -->
<div class="modal fade bs-example-modal-sm" id="ifHandle">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body">
                <p>确认删除？</p>
                <form name="" method="post" id="editObjForm" class="form-horizontal un-display">
                    <div class="form-group">
                        <label for="recipient-username" class="col-sm-2 control-label">名称:</label>
                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control" id="recipient-username">
                            <input type="hidden" name="id" value="">
                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btnConfirm" data-dismiss="modal">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade bs-example-modal-sm un-display" id="handleSuccess">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body">
                <p>删除成功！</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btnConfirm" data-dismiss="modal">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="admin-main">
    <div class="container-fluid">
        <%@ include file="../common/nav.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <ol class="breadcrumb">
                <li>商品管理</li>
                <li>设置分类</li>
            </ol>

            <div class="content">

                <div class="one-con">

                    <div class="one-content">

                        <div class="one-obj">
                            <div class="one-obj-title">
                                一级分类
                            </div>
                            <div class="one-obj-content">
                                <ul class="list-group">
                                    <c:forEach items="${cateList}" var="obj">
                                        <li class="list-group-item" onclick="fShowChildCate(this, 1)"
                                            onmouseover="fShowHandle(this)" onmouseout="fHideHandle(this)">
                                            ${obj.catename}
                                            <input type="hidden" name="id" value="${obj.id}">
                                            <a href="javascript:void(0)" class="btn-my-delete-icon delete-icon"
                                               data-toggle="modal" data-target="#ifHandle" data-whatever="${obj.id}-deleteCate">
                                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <a href="javascript:void(0)" onclick="fAddCate(this, 0, 1);" class="btn btn-my">添加</a>

                        </div>

                        <div class="one-obj">
                            <div class="one-obj-title">
                                二级分类
                            </div>
                            <div class="one-obj-content">
                                <ul class="list-group">
                                </ul>
                            </div>
                            <a href="javascript:void(0)" class="btn btn-my">添加</a>
                        </div>

                        <div class="one-obj">
                            <div class="one-obj-title">
                                三级分类
                            </div>
                            <div class="one-obj-content">
                                <ul class="list-group">
                                </ul>
                            </div>
                            <a href="javascript:void(0)" class="btn btn-my">添加</a>
                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/add-pro-cate.js"></script>

</body>
</html>

