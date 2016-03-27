<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/23
  Time: 下午4:54
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

    <title>订单管理</title>

    <!-- 公共样式 -->
    <%@ include file="../common/header-link.jsp" %>

    <link href="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/css/fileinput.min.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/style.css" rel="stylesheet">
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/orderList.css" rel="stylesheet">

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
                        <li class="list-group-item">项目编号:<span id="p-projectnum"></span></li>
                        <li class="list-group-item">订单号:<span id="p-tradeno"></span></li>
                        <li class="list-group-item">收货人:<span id="p-username"></span></li>
                        <li class="list-group-item">联系电话:<span id="p-phone"></span></li>
                        <li class="list-group-item">收货地址:<span id="p-address"></span></li>
                        <li class="list-group-item">支持件数:<span id="p-backnum"></span></li>
                        <li class="list-group-item">选择尺码:<span id="p-size"></span></li>
                        <li class="list-group-item">订单总额:<span id="p-price"></span></li>
                        <li class="list-group-item">订单状态:<span id="p-status"></span></li>
                        <form action="" method="post" id="expressForm">
                        <li class="list-group-item">快递公司:
                            <span id="p-express">
                                <%--<input type="text" class="form-control" placeholder="快递公司">--%>
                            </span>
                        </li>
                        <li class="list-group-item">运单号:
                            <span id="p-trackno">
                                <%--<input type="text" class="form-control" placeholder="运单号">--%>
                            </span>
                        </li>
                        </form>
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
            <%--<h1 class="page-header">项目管理</h1>--%>

            <ol class="breadcrumb">
                <li>订单管理</li>
            </ol>

            <div class="table-handler-con">
                <div class="table-handler">
                    批量操作
                    <select id="batchHandler" class="form-control">
                        <option value="deleteChoose">全部删除</option>
                    </select>
                    <a class="btn btn-my" onclick="CommonHandler.fBatchHandler('Order')">应用</a>
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
                        <th>订单号</th>
                        <th>收货人</th>
                        <th>金额/元</th>
                        <th>
                            <div class="btn-group">
                                <span class="btn btn-default dropdown-toggle dropdown-my" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    状态 <span class="caret"></span>
                                </span>
                                <ul class="dropdown-menu">
                                    <li><a href="admin/Order/selectAll.do?status=all">全部订单</a></li>
                                    <li><a href="admin/Order/selectAll.do?status=wait_deliver">待发货</a></li>
                                    <li><a href="admin/Order/selectAll.do?status=wait_take">待收货</a></li>
                                    <li><a href="admin/Order/selectAll.do?status=already_take">已收货</a></li>
                                </ul>
                            </div>
                        </th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orderList}" var="obj">
                        <tr>
                            <td>
                                <input type="checkbox" name="projects" value="${obj.order.id}">
                            </td>
                            <td>${obj.order.id}</td>
                            <td>${obj.project.projectno}</td>
                            <td>
                                <a href="javascript:void(0)" data-toggle="modal"
                                   data-target="#ifHandle" data-whatever="${obj.order.id}-check">
                                   ${obj.order.tradeno}
                                </a>
                            </td>
                            <td>${obj.order.username}</td>
                            <td>${obj.order.price}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.order.status == 0}">
                                        待发货
                                    </c:when>
                                    <c:when test="${obj.order.status == 1}">
                                        待收货
                                    </c:when>
                                    <c:when test="${obj.order.status == 2}">
                                        已收货
                                    </c:when>
                                    <c:when test="${obj.order.status == 3}">
                                        退款中
                                    </c:when>
                                    <c:when test="${obj.order.status == 4}">
                                        退款成功
                                    </c:when>
                                    <c:when test="${obj.order.status == 5}">
                                        退款失败
                                    </c:when>
                                    <c:when test="${obj.order.status == 6}">
                                        待支付
                                    </c:when>
                                    <c:otherwise>
                                        未知状态
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.order.status == 0}">
                                        <a href="javascript:void(0);" class="btn btn-my" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.order.id}-deliver">发货</a>
                                        <a href="javascript:void(0);" class="btn btn-my-edit" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.order.id}-delete">删除</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:void(0);" class="btn btn-my" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.order.id}-edit">修改</a>
                                        <a href="javascript:void(0);" class="btn btn-my-edit" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.order.id}-delete">删除</a>
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

<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/handler-order.js"></script>

</body>
</html>
