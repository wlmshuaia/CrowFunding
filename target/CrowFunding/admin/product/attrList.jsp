<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/09
  Time: 下午3:00
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

    <title>商品属性</title>

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
                <li>设置属性</li>
            </ol>

            <div class="content">

                <div class="one-con">
                    <div class="one-header">
                        <span>颜色</span>
                    </div>
                    <div class="one-content">
                        <form action="admin/Product/addColorHandle.do" name="addColorForm" method="post" class="form-horizontal">
                            <div class="form-group center">
                                <label for="colorname" class="control-label">添加新的颜色</label>
                                <div class="col-md-6">
                                    <input type="text" name="colorname" class="form-control" id="colorname" placeholder="颜色名称">
                                </div>
                                <div class="col-md-2">
                                    <input type="color" name="colorcard" class="form-control colorcard">
                                </div>
                                <input type="button" id="add-color" class="btn-my" value="添加">
                            </div>
                        </form>
                        <div class="table-container">
                            <table class="table table-hover center">
                                <thead>
                                    <tr>
                                        <th>编号</th>
                                        <th>颜色名称</th>
                                        <th>色卡</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${colorList}" var="obj">
                                        <tr>
                                            <td>${obj.id}</td>
                                            <td>${obj.colorname}</td>
                                            <td>
                                                <span style="background: ${obj.colorcard}; display: block; width: 15px; height: 15px;"></span>
                                            </td>
                                            <td>
                                                <a href="javascript:void(0)" class="btn btn-my" data-toggle="modal"
                                                   data-target="#ifHandle" data-whatever="${obj.id}-editColor">编辑</a>
                                                <a href="javascript:void(0)" class="btn btn-my-edit" data-toggle="modal"
                                                   data-target="#ifHandle" data-whatever="${obj.id}-deleteColor">删除</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>

                <div class="one-con">
                    <div class="one-header">
                        <span>尺码</span>
                    </div>
                    <div class="one-content">
                        <form action="admin/Product/addSizeHandle.do" name="addSizeForm" method="post" class="form-horizontal">
                            <div class="form-group center">
                                <label for="sizename" class="control-label">添加新的尺码</label>
                                <div class="col-md-6">
                                    <input type="text" name="sizename" class="form-control" id="sizename" placeholder="尺码名称">
                                </div>
                                <input type="button" id="add-size" class="btn-my" value="添加">
                            </div>
                        </form>
                        <div class="table-container">
                            <table class="table table-hover center">
                                <thead>
                                    <tr>
                                        <th>编号</th>
                                        <th>尺码名称</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${sizeList}" var="obj">
                                        <tr>
                                            <td>${obj.id}</td>
                                            <td>${obj.sizename}</td>
                                            <td>
                                                <a href="javascript:void(0)" class="btn btn-my" data-toggle="modal"
                                                   data-target="#ifHandle" data-whatever="${obj.id}-editSize">编辑</a>
                                                <a href="javascript:void(0)" class="btn btn-my-edit" data-toggle="modal"
                                                   data-target="#ifHandle" data-whatever="${obj.id}-deleteSize">删除</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>

            </div>

        </div>

    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/add-pro-attr.js"></script>

</body>
</html>
