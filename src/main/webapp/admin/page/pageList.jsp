<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/20
  Time: 下午4:06
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

    <title>首页轮播图</title>

    <!-- 公共样式 -->
    <%@ include file="../common/header-link.jsp" %>
    <link href="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/css/fileinput.min.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/style.css" rel="stylesheet">

</head>
<body>
<%@ include file="../common/header.jsp" %>

<!-- 模态框 -->
<div class="modal fade bs-example-modal-sm" id="ifHandle">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body">
                <p>确认删除？</p>

                <form name="" method="post" id="editObjForm" class="form-horizontal" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="recipient-username" class="col-sm-2 control-label">名称:</label>

                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control" id="recipient-username">
                            <input type="hidden" name="id" value="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">海报:</label>
                        <div class="col-sm-10 center-align">
                            <div id="kv-avatar-errors" class="center-block" style="width:800px;display:none"></div>
                            <div class="kv-avatar" id="poster-con" style="width:200px">
                                <input id="poster" name="poster" type="file" class="file-loading">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">链接:</label>
                        <div class="col-sm-10" id="theme-label">
                            <input type="text" name="linkurl" class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否显示:</label>
                        <div class="col-sm-10">
                            <label><input type="radio" name="status" value="0" > 关闭</label>
                            <label><input type="radio" name="status" value="1" > 显示</label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btnConfirm" data-dismiss="modal">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade bs-example-modal-sm un-display" id="handleSuccess">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
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
                <li>首页管理</li>
                <li>首页轮播图</li>
            </ol>

            <div class="table-handler-con">
                <div class="table-handler">
                    批量操作
                    <select id="batchHandler" class="form-control">
                        <option value="deleteChoose">全部删除</option>
                    </select>
                    <a class="btn btn-my" onclick="CommonHandler.fBatchHandler('Page')">应用</a>
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
                        <th>名称</th>
                        <th>海报</th>
                        <th>链接地址</th>
                        <th>是否显示</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageList}" var="obj">
                        <tr>
                            <td>
                                <input type="checkbox" name="pros" value="${obj.id}">
                            </td>
                            <td>${obj.id}</td>
                            <td>
                                <a href="javascript:void(0)" data-toggle="modal"
                                   data-target="#ifHandle" data-whatever="${obj.id}-check">
                                        ${obj.name}
                                </a>
                            </td>
                            <td>
                                <img src="<%=DataUtils.DOMAIN_FILE%>/${obj.posterurl}" width="150" height="100">
                            </td>
                            <td>
                                <div class="box">
                                    ${obj.linkurl}
                                </div>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.status eq '1'}">
                                        显示
                                    </c:when>
                                    <c:otherwise>
                                        关闭
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="javascript:void(0)" class="btn btn-my" data-toggle="modal"
                                   data-target="#ifHandle" data-whatever="${obj.id}-edit">修改</a>
                                <a href="javascript:void(0)" class="btn btn-my-edit" data-toggle="modal"
                                   data-target="#ifHandle" data-whatever="${obj.id}-delete">删除</a>
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

<script src="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/js/fileinput.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/js/fileinput_locale_zh.js"></script>

<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/handler-page.js"></script>

</body>
</html>


