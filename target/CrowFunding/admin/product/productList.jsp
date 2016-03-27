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
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>全部商品</title>

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

                <form name="" method="post" id="editUserForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="recipient-username" class="col-sm-2 control-label">用户名:</label>

                        <div class="col-sm-10">
                            <input type="text" name="adminname" class="form-control" id="recipient-username">
                            <input type="hidden" name="id" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="recipient-age" class="col-sm-2 control-label">密码:</label>

                        <div class="col-sm-10">
                            <input type="text" name="password" class="form-control" id="recipient-age">
                        </div>
                    </div>
                </form>

                <div class="obj-info">
                    <div class="obj-img">
                        <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/img.png" alt="">
                    </div>
                    <ul class="list-group">
                        <li class="list-group-item">商品名称:<span class="p-proname"></span></li>
                        <li class="list-group-item">商品货号:<span class="p-prono"></span></li>
                        <li class="list-group-item">分类:<span class="p-cate"></span></li>
                        <li class="list-group-item">尺码:<span class="p-size"></span></li>
                        <li class="list-group-item">颜色:<span class="p-color"></span></li>
                        <li class="list-group-item">价格:<span class="p-price"></span></li>
                        <li class="list-group-item">最低筹集件数:<span class="p-leastnum"></span></li>
                        <li class="list-group-item">洗涤说明:<span class="p-care"></span></li>
                        <li class="list-group-item">尺码表:<span class="p-sizetable"></span></li>
                    </ul>
                </div>

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
            <%--<h1 class="page-header">商品管理</h1>--%>

            <ol class="breadcrumb">
                <li>商品管理</li>
                <li>全部商品</li>
            </ol>

            <div class="table-handler-con">
                <div class="table-handler">
                    批量操作
                    <select id="batchHandler" class="form-control">
                        <option value="deleteChoose">全部删除</option>
                    </select>
                    <a class="btn btn-my" onclick="CommonHandler.fBatchHandler('Product')">应用</a>
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
                        <th>商品名称</th>
                        <th>货号</th>
                        <th>品类</th>
                        <th>价格/元</th>
                        <th>最低筹集</th>
                        <th>
                            <div class="btn-group">
                                <span class="btn btn-default dropdown-toggle dropdown-my" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    状态 <span class="caret"></span>
                                </span>
                                <ul class="dropdown-menu">
                                    <li><a href="admin/Product/selectAll.do?status=all">全部商品</a></li>
                                    <li><a href="admin/Product/selectAll.do?status=onSale">在售中</a></li>
                                    <li><a href="admin/Product/selectAll.do?status=inWarehouse">在仓库</a></li>
                                </ul>
                            </div>
                        </th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${productList}" var="obj">
                            <tr>
                                <td>
                                    <input type="checkbox" name="pros" value="${obj.id}">
                                </td>
                                <td>${obj.id}</td>
                                <td>
                                    <a href="javascript:void(0)" data-toggle="modal" data-target="#ifHandle" data-whatever="${obj.id}-check">
                                        ${obj.proname}
                                    </a>
                                </td>
                                <td>${obj.prono}</td>
                                <td>${obj.cateid}</td>
                                <td>${obj.price}</td>
                                <td>${obj.leastnum} 件</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${obj.status == 1}">
                                            在售中
                                        </c:when>
                                        <c:otherwise>
                                            在仓库
                                        </c:otherwise>
                                    </c:choose>
                                    <%--在售中--%>
                                </td>
                                <td>
                                    <a href="javascript:void(0)" class="btn btn-my" data-toggle="modal"
                                       data-target="#ifHandle" data-whatever="${obj.id}-edit">修改</a>
                                    <c:choose>
                                        <c:when test="${obj.status == 1}">
                                            <a href="javascript:void(0)" class="btn btn-my-edit" data-toggle="modal" data-target="#ifHandle"
                                               data-whatever="${obj.id}-off">下架</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="javascript:void(0)" class="btn btn-my-edit" data-toggle="modal" data-target="#ifHandle"
                                               data-whatever="${obj.id}-on">上架</a>
                                        </c:otherwise>
                                    </c:choose>
                                    <a href="javascript:void(0)" class="btn btn-my-delete" data-toggle="modal" data-target="#ifHandle"
                                       data-whatever="${obj.id}-delete">删除</a>
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
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/handler-product.js"></script>

</body>
</html>
