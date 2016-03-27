<%@ page import="com.tide.utils.DataUtils" %><%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/29
  Time: 上午11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <title>项目热议</title>

    <!-- 公共样式 -->
    <%@ include file="../common/header-link.jsp" %>

    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/style.css" rel="stylesheet">

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
                    <%--<div class="form-group">--%>
                        <%--<label for="recipient-username" class="col-sm-2 control-label">项目名称:</label>--%>
                        <%--<div class="col-sm-10">--%>
                            <%--<input type="text" name="title" class="form-control" id="recipient-username">--%>
                            <%--<input type="hidden" name="id" value="">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                </form>

                <div class="obj-info">

                    <ul class="list-group">
                        <%--<li class="list-group-item">项目编号:<span id="p-projectnum"></span></li>--%>
                    </ul>
                </div>
            </div>

            <div class="modal-footer">
                <div id="handler-group">
                    <button type="button" class="btn btn-primary btnConfirm" data-dismiss="modal">确认</button>
                    <button type="button" class="btn btn-default btnCancel" data-dismiss="modal">取消</button>
                </div>

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
                <li>消息中心</li>
                <li>项目热议</li>
            </ol>

            <div class="table-handler-con">
                <div class="table-handler">
                    批量操作
                    <select id="batchHandler" class="form-control">
                        <option value="deleteChoose">全部删除</option>
                    </select>
                    <a class="btn btn-my" onclick="CommonHandler.fBatchHandler('Comment')">应用</a>
                </div>
            </div>

            <div class="search-con">
                <div class="row">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search for...">
                  <span class="input-group-btn">
                    <button class="btn btn-default" type="button">搜索</button>
                  </span>
                    </div><!-- /input-group -->
                </div><!-- /.row -->
            </div>

            <div class="table-container">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>
                            <input type="checkbox" id="chooseAll">
                        </th>
                        <th>编号</th>
                        <th>项目编号</th>
                        <th>项目名称</th>
                        <th>发布者</th>
                        <th>评论</th>
                        <th>
                            <div class="btn-group">
                                <span class="btn btn-default dropdown-toggle dropdown-my" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    状态 <span class="caret"></span>
                                </span>
                                <ul class="dropdown-menu">
                                    <li><a href="admin/Comment/selectAll.do?status=all">全部评论</a></li>
                                    <li><a href="admin/Comment/selectAll.do?status=wait_exame">待审核</a></li>
                                    <li><a href="admin/Comment/selectAll.do?status=already_pass">已通过</a></li>
                                    <li><a href="admin/Comment/selectAll.do?status=already_against">已否决</a></li>
                                </ul>
                            </div>
                        </th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${commentList}" var="obj">
                        <tr>
                            <td>
                                <input type="checkbox" name="projects" value="${obj.comment.id}">
                            </td>
                            <td>${obj.comment.id}</td>
                            <td>${obj.project.projectno}</td>
                            <td>${obj.project.title}</td>
                            <td>${obj.user.nickname}</td>
                            <td>${obj.comment.content}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.comment.status == 1}">
                                        通过
                                    </c:when>
                                    <c:when test="${obj.comment.status == 2}">
                                        否决
                                    </c:when>
                                    <c:when test="${obj.comment.status == 0}">
                                        待审核
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.comment.status == 0}">
                                        <a href="javascript:void(0);" class="btn btn-my-success" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.comment.id}-pass">通过</a>
                                        <a href="javascript:void(0);" class="btn btn-my-delete" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.comment.id}-against">否决</a>
                                    </c:when>
                                </c:choose>
                                <a href="javascript:void(0);" class="btn btn-my" data-toggle="modal"
                                   data-target="#ifHandle" data-whatever="${obj.comment.id}-edit">修改</a>
                                <a href="javascript:void(0);" class="btn btn-my-edit" data-toggle="modal"
                                   data-target="#ifHandle" data-whatever="${obj.comment.id}-delete">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <%@ include file="../common/page.jsp"%>

        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/handler-comment.js"></script>

</body>
</html>

