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

    <title>添加商品</title>

    <!-- 公共样式 -->
    <%@ include file="../common/header-link.jsp" %>
    <link href="<%=DataUtils.DOMAIN_FILE%>/assets/fileInput/css/fileinput.min.css" rel="stylesheet">
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
                <%--<p>确认删除？</p>--%>
                <%--<form name="" method="post" id="editObjForm" class="form-horizontal">--%>
                <%--<div class="form-group">--%>
                <%--<label for="recipient-username" class="col-sm-2 control-label">用户名:</label>--%>
                <%--<div class="col-sm-10">--%>
                <%--<input type="text" name="adminname" class="form-control" id="recipient-username">--%>
                <%--<input type="hidden" name="id" value="">--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                <%--<label for="recipient-age" class="col-sm-2 control-label">密码:</label>--%>
                <%--<div class="col-sm-10">--%>
                <%--<input type="text" name="password" class="form-control" id="recipient-age">--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</form>--%>
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
                <li>添加商品</li>
            </ol>

            <div class="content">
                <form action="admin/Product/addProductHandle.do" method="post" class="form-horizontal" enctype="multipart/form-data">
                    <div class="one-con">
                        <div class="one-header">
                            <span>基本信息</span>
                        </div>
                        <div class="one-content">
                            <div class="form-group">
                                <label for="proname" class="control-label"><span class="red">*</span>商品名称</label>
                                <div class="col-md-4">
                                    <input type="text" name="proname" class="form-control" id="proname" placeholder="名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="prono" class="control-label"><span class="red">*</span>商品货号</label>
                                <div class="col-md-4">
                                    <input type="text" name="prono" class="form-control" id="prono" placeholder="货号">
                                </div>
                            </div>
                            <div class="form-group select">
                                <label for="cate" class="control-label"><span class="red">*</span>所属分类</label>
                                <div class="col-md-8">
                                    <select name="" id="cate" class="form-control" onchange="fShowChildCate(this, 1)">
                                        <option value="">一级分类</option>
                                        <c:forEach items="${cateList}" var="obj">
                                            <option value="${obj.id}">${obj.catename}</option>
                                        </c:forEach>
                                    </select>
                                    <select name="" class="form-control" onchange="fShowChildCate(this, 2)">
                                        <option value="">二级分类</option>
                                    </select>
                                    <select name="cateid" class="form-control" onchange="fShowChildCate(this, 3)">
                                        <option value="">三级分类</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="one-con">
                        <div class="one-header">
                            <span>规格属性</span>
                        </div>
                        <div class="one-content">
                            <div class="form-group">
                                <label for="proname" class="control-label"><span class="red">*</span>颜色</label>
                                <div class="col-md-8 checkbox-con">

                                    <c:forEach items="${colorList}" var="obj">
                                        <label class="checkbox-inline">
                                            <input type="checkbox" name="color" value="${obj.id}">
                                            <span class="color-name">${obj.colorname}</span>
                                        </label>
                                    </c:forEach>

                                </div>
                            </div>
                            <div class="form-group">
                                <label for="prono" class="control-label"><span class="red">*</span>尺码<br>(重量)</label>
                                <div class="col-md-8 checkbox-con">

                                    <c:forEach items="${sizeList}" var="obj">
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="inlineCheckbox3" name="size" value="${obj.id}"> ${obj.sizename}
                                        </label>
                                        <input type="text" class="form-control input-sm" name="weight-${obj.id}"> <span class="label-weight">千克</span>
                                    </c:forEach>

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="one-con">
                        <div class="one-header">
                            <span>筹销信息</span>
                        </div>
                        <div class="one-content">
                            <div class="form-group">
                                <label for="proname" class="control-label"><span class="red">*</span>商品价格</label>
                                <div class="col-md-4">
                                    <input type="text" name="price" class="form-control" placeholder="价格">
                                </div>
                                元
                            </div>
                            <div class="form-group">
                                <label for="prono" class="control-label"><span class="red">*</span>最低筹集件数</label>
                                <div class="col-md-4">
                                    <input type="text" name="leastnum" class="form-control" placeholder="件数">
                                </div>
                                件
                            </div>
                        </div>
                    </div>

                    <div class="one-con">
                        <div class="one-header">
                            <span>图片信息</span>
                        </div>
                        <div class="one-content">
                            <div class="form-group">
                                <label for="proname" class="control-label"><span class="red">*</span>主图</label>
                                <div class="col-md-4">
                                    <input id="input-mainimg" type="file" name="mainimgFile" class="file-loading">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="prono" class="control-label"><span class="red">*</span>规格主图</label>
                                <div class="col-md-10">
                                    <div class="specify-con">
                                        <div class="specify">
                                            <div class="specify-tip center">
                                                请先选择规格属性,再上传规格主图
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="prono" class="control-label"><span class="red">*</span>详图</label>
                                <div class="col-md-11 file-my-multi">
                                    <input id="input-multi-detail" name="detailimgs" type="file" multiple class="file-loading">
                                </div>
                            </div>

                        </div>
                    </div>

                    <div class="one-con">
                        <div class="one-header">
                            <span>其他</span>
                        </div>
                        <div class="one-content">
                            <div class="form-group">
                                <label for="care" class="control-label"><span class="red">*</span>洗护</label>
                                <div class="col-md-8 text-area">
                                    <textarea name="care" class="form-control" id="care" cols="30" rows="10"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="input-multi-sizetable" class="control-label"><span class="red">*</span>尺码</label>
                                <div class="col-md-11 file-my-multi">
                                    <input id="input-multi-sizetable" name="sizetables" type="file" multiple class="file-loading">
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

<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/admin/add-pro-base.js"></script>

</body>
</html>
