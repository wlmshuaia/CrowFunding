<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/11/30
  Time: 下午2:52
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

    <title>项目列表</title>

    <!-- 公共样式 -->
    <%@ include file="../common/header-link.jsp" %>

    <link href="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/css/fileinput.min.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/style.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/projectList.css" rel="stylesheet">

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

                <form name="" method="post" id="editObjForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="recipient-username" class="col-sm-2 control-label">项目名称:</label>

                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" id="recipient-username">
                            <input type="hidden" name="id" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">项目详情:</label>

                        <div class="col-sm-10">
                            <textarea name="intro" class="form-control" cols="30" rows="10"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">效果图:</label>

                        <div class="col-sm-10 center-align">
                            <div id="kv-avatar-errors" class="center-block" style="width:800px;display:none"></div>
                            <div class="kv-avatar" id="designimg-con" style="width:200px">
                                <input id="designimg" name="designimg" type="file" class="file-loading">
                            </div>
                            <%--<img src="" alt="" id="designimg">--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">商品:</label>

                        <div class="col-sm-10">
                            <select name="product" id="" class="form-control">
                                <option value="">选择商品</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">颜色:</label>

                        <div class="col-sm-10">
                            <select name="color" class="form-control">
                                <option value="">选择颜色</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">价格:</label>

                        <div class="col-sm-10">
                            <span id="pro-price" class="red"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">标签:</label>

                        <div class="col-sm-10" id="labelList">
                            <label><input type="checkbox"> 圣诞</label>
                            <label><input type="checkbox"> 涂鸦</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">结束日期:</label>

                        <div class="col-sm-10">
                            <input type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">目标件数:</label>

                        <div class="col-sm-10">
                            <input type="number" name="targetnum" class="form-control">
                        </div>
                    </div>
                </form>

                <div class="obj-info">
                    <div class="obj-img">
                        <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/icon_person.png" alt="">
                    </div>
                    <ul class="list-group">
                        <li class="list-group-item">项目名称:<span id="p-title"></span></li>
                        <li class="list-group-item">项目编号:<span id="p-projectnum"></span></li>
                        <li class="list-group-item">项目详情:<span id="p-intro"></span></li>
                        <li class="list-group-item">商品:<span id="p-product"></span></li>
                        <li class="list-group-item">颜色:<span id="p-color"></span></li>
                        <li class="list-group-item">价格:<span id="p-price"></span></li>
                        <li class="list-group-item">标签:<span id="p-label"></span></li>
                        <li class="list-group-item">
                            <i class="user-designer">*</i>
                            发布者:<span id="p-user"></span>
                        </li>
                        <li class="list-group-item">起止日期:<span id="p-date"></span></li>
                        <li class="list-group-item">目标件数:<span id="p-targetnum"></span></li>
                        <li class="list-group-item">状态:<span id="p-status"></span></li>
                    </ul>
                </div>
            </div>

            <div class="modal-footer">
                <span class="obj-tip">带
                    <span class="red">*</span>
                    号的为设计师
                </span>

                <div id="handler-group">
                    <button type="button" class="btn btn-success btnPass" data-dismiss="modal">通过</button>
                    <button type="button" class="btn btn-warning btnUnpass" data-dismiss="modal">否决</button>
                    <button type="button" class="btn btn-primary btnEdit" data-dismiss="modal">修改</button>
                    <button type="button" class="btn btn-danger btnDelete" data-dismiss="modal">删除</button>
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
            <%--<h1 class="page-header">项目管理</h1>--%>

            <ol class="breadcrumb">
                <li>项目管理</li>
                <li>全部项目</li>
            </ol>

            <div class="table-handler-con">
                <div class="table-handler">
                    批量操作
                    <select id="batchHandler" class="form-control">
                        <option value="deleteChoose">全部删除</option>
                    </select>
                    <a class="btn btn-my" onclick="CommonHandler.fBatchHandler('Project')">应用</a>
                </div>
                <div class="table-handler">
                    用户类型
                    <select name="" class="form-control">
                        <option value="">所有用户</option>
                        <option value="0">普通用户</option>
                        <option value="1">设计师</option>
                    </select>
                    <button class="btn-my">筛选</button>
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
                        <th>项目名称</th>
                        <th>效果图</th>
                        <th>商品</th>
                        <th>发布者</th>
                        <th>起止日期</th>
                        <th>
                            <div class="btn-group">
                                <span class="btn btn-default dropdown-toggle dropdown-my" data-toggle="dropdown"
                                      aria-haspopup="true" aria-expanded="false">
                                    类型 <span class="caret"></span>
                                </span>
                                <ul class="dropdown-menu">
                                    <li><a href="admin/Project/selectAll.do?type=all">全部</a></li>
                                    <li><a href="admin/Project/selectAll.do?type=cloth">服装</a></li>
                                    <li><a href="admin/Project/selectAll.do?type=commonweal">公益</a></li>
                                </ul>
                            </div>
                        </th>
                        <th>目标</th>
                        <th>支持人数</th>
                        <th>已筹集</th>
                        <th>
                            <div class="btn-group">
                                <span class="btn btn-default dropdown-toggle dropdown-my" data-toggle="dropdown"
                                      aria-haspopup="true" aria-expanded="false">
                                    状态 <span class="caret"></span>
                                </span>
                                <ul class="dropdown-menu">
                                    <li><a href="admin/Project/selectAll.do?status=all">全部项目</a></li>
                                    <li><a href="admin/Project/selectAll.do?status=wait_exame">审核中</a></li>
                                    <li><a href="admin/Project/selectAll.do?status=crowfunding">进行中</a></li>
                                    <li><a href="admin/Project/selectAll.do?status=success">已达成</a></li>
                                    <li><a href="admin/Project/selectAll.do?status=fail">未达成</a></li>
                                </ul>
                            </div>
                        </th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${projectList}" var="obj">
                        <tr>
                            <td>
                                <input type="checkbox" name="projects" value="${obj.project.id}">
                            </td>
                            <td>${obj.project.id}</td>
                            <td>
                                <a href="javascript:void(0)" data-toggle="modal" data-target="#ifHandle"
                                   data-whatever="${obj.project.id}-check">
                                        ${obj.project.title}
                                </a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.designimg != null}">
                                        <img src="<%=DataUtils.DOMAIN_FILE%>/${obj.designimg}" width=100 height=100>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/img.png" width=100 height=100>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${obj.product.proname}</td>
                            <td>${obj.user.nickname}</td>
                            <td>${obj.project.releasedate}
                                <br>
                                —
                                <br>
                                    ${obj.project.enddate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.project.type == 0}">
                                        服装
                                    </c:when>
                                    <c:otherwise>
                                        公益
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                ${obj.project.targetnum}
                                <c:choose>
                                    <c:when test="${obj.project.type == 1}">
                                        人
                                    </c:when>
                                    <c:when test="${obj.project.type == 2}">
                                        元
                                    </c:when>
                                    <c:otherwise>
                                        件
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.project.backernum != null}">
                                        ${obj.project.backernum} 人
                                    </c:when>
                                    <c:otherwise>
                                        0 人
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.project.fundnum != null}">
                                        ${obj.project.fundnum}
                                    </c:when>
                                    <c:otherwise>
                                        0
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${obj.project.type == 1}">
                                        人
                                    </c:when>
                                    <c:when test="${obj.project.type == 2}">
                                        元
                                    </c:when>
                                    <c:otherwise>
                                        件
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.project.status == 1}">
                                        进行中
                                    </c:when>
                                    <c:when test="${obj.project.status == 2}">
                                        被否决
                                    </c:when>
                                    <c:when test="${obj.project.status == 3}">
                                        已达成
                                    </c:when>
                                    <c:when test="${obj.project.status == 4}">
                                        未达成
                                    </c:when>
                                    <c:otherwise>
                                        审核中
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.project.status == 0}">
                                        <a href="javascript:void(0);" class="btn btn-my" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.project.id}-pass">通过</a>
                                        <a href="javascript:void(0);" class="btn btn-my-edit" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.project.id}-against">否决</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:void(0);" class="btn btn-my" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.project.id}-edit">修改</a>
                                        <a href="javascript:void(0);" class="btn btn-my-edit" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.project.id}-delete">删除</a>
                                    </c:otherwise>
                                </c:choose>
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
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/handler-project.js"></script>

</body>
</html>
