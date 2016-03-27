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

    <title>用户列表</title>

    <!-- 公共样式 -->
    <%@ include file="../common/header-link.jsp" %>

    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/style.css" rel="stylesheet">

    <style>
        .avatar {
            border: none!important;
        }
        .avatar img {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            border: #ddd 1px solid;
        }
    </style>

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
                    <input type="hidden" name="id" value="">
                    <input type="hidden" name="openid" value="">
                    <input type="hidden" name="headimgurl" value="">

                    <div class="form-group align-center">
                        <div class="avatar">
                            <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/icon_person.png" alt="">
                            <a href="javascript:void(0)" onclick="Handler.fToChangeAvatar(this)">修改头像</a>
                            <input type="file" name="headimg" onchange="Handler.fShowAvatar(this)" style="width: 0; height: 0;">
                            <p>openid: <span id="openid"></span></p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="recipient-username" class="col-sm-2 control-label">昵称:</label>
                        <div class="col-sm-10">
                            <input type="text" name="nickname" class="form-control" id="recipient-username">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">性别:</label>
                        <div class="col-sm-10">
                            <label><input type="radio" name="sex" value="0"> 未知</label>
                            <label><input type="radio" name="sex" value="1"> 男</label>
                            <label><input type="radio" name="sex" value="2"> 女</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">手机:</label>
                        <div class="col-sm-10">
                            <input type="text" name="phone" class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">省份:</label>
                        <div class="col-sm-10">
                            <input type="text" name="province" class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">城市:</label>
                        <div class="col-sm-10">
                            <input type="text" name="city" class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">语言:</label>
                        <div class="col-sm-10">
                            <input type="text" name="language" class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">简介:</label>
                        <div class="col-sm-10">
                            <textarea name="intro" class="form-control" id="" cols="30" rows="10"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户类型:</label>

                        <div class="col-sm-10">
                            <label><input type="radio" name="type" value="0"> 普通用户</label>
                            <label><input type="radio" name="type" value="1"> 设计师</label>
                        </div>
                    </div>
                </form>

                <div class="obj-info">
                    <div class="obj-img avatar">
                        <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/icon_person.png" alt="">
                    </div>
                    <ul class="list-group">
                        <li class="list-group-item">openid:<span id="p-openid"></span></li>
                        <li class="list-group-item">昵称:<span id="p-nickname"></span></li>
                        <li class="list-group-item">性别:<span id="p-sex"></span></li>
                        <li class="list-group-item">手机:<span id="p-phone"></span></li>
                        <li class="list-group-item">地址:<span id="p-address"></span></li>
                        <li class="list-group-item">语言:<span id="p-language"></span></li>
                        <li class="list-group-item">简介:<span id="p-intro"></span></li>
                        <li class="list-group-item">类型:<span id="p-type"></span></li>
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
                <li>用户管理</li>
                <li>基本资料</li>
            </ol>

            <div class="table-handler-con">
                <div class="table-handler">
                    批量操作
                    <select id="batchHandler" class="form-control">
                        <option value="deleteChoose">全部删除</option>
                    </select>
                    <button class="btn-my">应用</button>
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
                        <th>微信openid</th>
                        <th>昵称</th>
                        <th>头像</th>
                        <th>性别</th>
                        <th>手机</th>
                        <th>地址</th>
                        <th>等级</th>
                        <th>
                            <div class="btn-group">
                                <span class="btn btn-default dropdown-toggle dropdown-my" data-toggle="dropdown"
                                      aria-haspopup="true" aria-expanded="false">
                                    用户类型 <span class="caret"></span>
                                </span>
                                <ul class="dropdown-menu">
                                    <li><a href="admin/User/selectAll.do?type=all">所有用户</a></li>
                                    <li><a href="admin/User/selectAll.do?type=normal">普通用户</a></li>
                                    <li><a href="admin/User/selectAll.do?type=designer">设计师</a></li>
                                </ul>
                            </div>
                        </th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userList}" var="obj">
                        <tr>
                            <td>
                                <input type="checkbox" name="projects">
                            </td>
                            <td>${obj.id}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.openid != null}">
                                        <a href="javascript:void(0);" data-toggle="modal"
                                           data-target="#ifHandle" data-whatever="${obj.id}-check">
                                            ${obj.openid}
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        暂无
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${obj.nickname}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.headimgurl != null}">
                                        <img src="${obj.headimgurl}" width=100 height=100>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/icon_person.png" width=100 height=100>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.sex == 1}">
                                        男
                                    </c:when>
                                    <c:when test="${obj.sex == 2}">
                                        女
                                    </c:when>
                                    <c:otherwise>
                                        未知
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${obj.phone}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.province != null}">
                                        ${obj.province} ${obj.city}
                                    </c:when>
                                    <c:otherwise>
                                        暂无
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>v1</td>
                            <td>
                                <c:choose>
                                    <c:when test="${obj.type == 1}">
                                        设计师
                                    </c:when>
                                    <c:when test="${obj.type == 0}">
                                        普通用户
                                    </c:when>
                                    <c:otherwise>
                                        未知类型
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="javascript:void(0);" class="btn btn-my" data-toggle="modal"
                                   data-target="#ifHandle" data-whatever="${obj.id}-edit">修改</a>
                                <a href="javascript:void(0);" class="btn btn-my-edit" data-toggle="modal"
                                   data-target="#ifHandle" data-whatever="${obj.id}-delete">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <%@ include file="../common/page.jsp" %>

        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/handler-user.js"></script>

</body>
</html>
