<%@ page import="java.util.Map" %>
<%@ page import="com.tide.bean.Project" %><%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/21
  Time: 下午2:57
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
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=false">

    <title>${projectMap.project.title}</title>

    <%@ include file="../common/header-link.jsp"%>

    <!-- Custom styles for this template -->
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/projectInfo.css" rel="stylesheet">
    <%--<link href="dist/css/front/projectInfo.css" rel="stylesheet">--%>

</head>
<body>

<%
    Map<String, Object> map = (Map<String, Object>) request.getAttribute("projectMap");
    Project project = (Project) map.get("project");
    String sCompany = DataUtils.getProjectType(project.getType());

    String sType = "T恤";
    if(project.getType() != DataUtils.ADD_PROJECT_TYPE_CLOTH_I) {
        sType = "项目";
    }
%>

    <div class="p-img">
        <c:choose>
            <c:when test="${projectMap.designimg.size() > 1}">
                <div class="am-slider am-slider-a1" data-am-flexslider  data-am-slider='{&quot;directionNav&quot;:false}'>
                    <ul class="am-slides">
                        <c:forEach items="${projectMap.designimg}" var="obj">
                            <li>
                                <img src="<%=DataUtils.DOMAIN_FILE%>/${obj.projectimg}">
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:when>
            <c:when test="${projectMap.designimg.size() == 1}">
                <img src="<%=DataUtils.DOMAIN_FILE%>/${projectMap.designimg.get(0).projectimg}" alt="">
            </c:when>
            <c:otherwise>
                <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/img.png" alt="">
            </c:otherwise>
        </c:choose>

        <div class="over-con left">
            <c:choose>
                <c:when test="${projectMap.project.status == 0}">
                    审核中
                </c:when>
                <c:when test="${projectMap.project.status == 1}">
                    筹集中
                </c:when>
                <c:when test="${projectMap.project.status == 2}">
                    已否决
                </c:when>
                <c:when test="${projectMap.project.status == 3}">
                    已达成
                </c:when>
                <c:when test="${projectMap.project.status == 4}">
                    未达成
                </c:when>
                <c:otherwise>
                    未知
                </c:otherwise>
            </c:choose>
            <span class="right-arrow-top"></span>
            <span class="right-arrow-bottom"></span>
        </div>

        <c:choose>
            <c:when test="${projectMap.ifFocus == 1}">
                <div class="over-con-on right" onclick="CommonHandler.fCancelFocusProject(this, ${projectMap.project.id})"></div>
            </c:when>
            <c:otherwise>
              <div class="over-con right" onclick="CommonHandler.fFocusProject(this, ${projectMap.project.id})"></div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="main">

        <div class="p-info">
            <div class="p-title">
                <h4>
                    ${projectMap.project.title}
                </h4>
            </div>
            <div class="price-con">
                <span class="p-price">
                    <%--价格: ${projectMap.product.price}元--%>
                    筹集目标: ${projectMap.project.targetnum}<%=sCompany%>
                </span>
                <span class="p-user">
                    <c:choose>
                        <c:when test="${projectMap.user.headimgurl.indexOf('http') > -1}">
                            <img src="${projectMap.user.headimgurl}" alt="">
                        </c:when>
                        <c:when test="${projectMap.user.headimgurl.indexOf('http') == -1}">
                            <img src="<%=DataUtils.DOMAIN_FILE%>/${projectMap.user.headimgurl}" alt="">
                        </c:when>
                        <c:otherwise>
                            <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/icon_person.png" alt="">
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${projectMap.user.type == 1}">
                        <div class="icon designer-mark"></div>
                    </c:if>
                    发布者: ${projectMap.user.nickname}
                </span>
            </div>
            <div class="p-intro">
                ${projectMap.project.intro}
            </div>
            <div class="p-labels">
                <%--<i class="am-icon-tag"></i>--%>
                <span class="myicon-tag"></span>
                <c:forEach items="${projectMap.labelList}" var="obj" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index == 0}">
                            ${obj.labelname}
                        </c:when>
                        <c:otherwise>
                            ${obj.labelname}
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${projectMap.project.type == 0}">
                    <div class="right">价格:${projectMap.product.price}元</div>
                </c:if>
            </div>
            <div class="p-cate">
                <%--分类: ${projectMap.product.proname}--%>
                分类: ${projectMap.catename}
            </div>
            <div class="progress">
                <div class="am-progress-bar am-progress-bar-warning" style="width: ${projectMap.percent}%;"></div>
            </div>
            <div class="p-progress">
                <ul class="am-avg-sm-4 am-thumbnails">
                    <li>
                        已达成${projectMap.percent}%
                    </li>
                    <li>
                        已筹${projectMap.fundnum}<%=sCompany%>
                    </li>
                    <li>
                        ${projectMap.backernum}人支持
                    </li>
                    <li>
                        剩余${projectMap.leftdays}天
                    </li>
                </ul>
            </div>
        </div>

        <a href="front/Product/productInfo.do?id=${projectMap.product.id}">
            <div class="about">
                关于<%=sType%>
                <span class="am-icon-chevron-right"></span>
            </div>
        </a>

        <c:choose>
            <c:when test="${projectMap.backernum > 0}">
                <a href="front/Project/getBackers.do?id=${projectMap.project.id}">
                    <div class="about">
                        支持ta的人(${projectMap.backernum})
                        <span class="am-icon-chevron-right"></span>
                    </div>
                </a>
            </c:when>
            <c:otherwise>
                <div class="about">
                    支持ta的人(0)
                    <span class="am-icon-chevron-right"></span>
                </div>
            </c:otherwise>
        </c:choose>

        <a href="front/Project/getComments.do?id=${projectMap.project.id}">
            <div class="about">
                小伙伴们说(${projectMap.commentnum})
                <span class="am-icon-chevron-right"></span>
            </div>
        </a>

        <%--<c:choose>--%>
            <%--<c:when test="${projectMap.commentnum > 0}">--%>
                <%--<a href="front/Project/getComments.do?id=${projectMap.project.id}">--%>
                    <%--<div class="about">--%>
                        <%--小伙伴们说(${projectMap.commentnum})--%>
                        <%--<span class="am-icon-chevron-right"></span>--%>
                    <%--</div>--%>
                <%--</a>--%>
            <%--</c:when>--%>
            <%--<c:otherwise>--%>
                <%--<div class="about">--%>
                    <%--小伙伴们说(0)--%>
                    <%--<span class="am-icon-chevron-right"></span>--%>
                <%--</div>--%>
            <%--</c:otherwise>--%>
        <%--</c:choose>--%>

        <%--<div class="guess-like">--%>
            <%--<label class="about">猜你喜欢</label>--%>

            <%--<ul class="am-avg-sm-3 am-thumbnails">--%>
                <%--<li>--%>
                    <%--<img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/test.png" alt="">--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/test.png" alt="">--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/test.png" alt="">--%>
                <%--</li>--%>
            <%--</ul>--%>

        <%--</div>--%>

    </div>

    <div id="showInfo" class="showInfo">
        <form method="post" class="am-form am-form-horizontal">
            <%--<div class="one-obj-info" id="product-info">--%>
                <%--<span class="left"></span>--%>
                <%--<span class="right">¥--%>
                    <%--<span id="totalPrice">0</span>--%>
                    <%--<input type="hidden" name="pro-price" value="0">--%>
                <%--</span>--%>
            <%--</div>--%>

                <c:choose>
                    <c:when test="${projectMap.project.type == 1}">
                        <h3 class="gray">请填写您的真实信息</h3>
                        <div class="one-obj-info am-g am-form-group">
                            <span class="one-obj-left am-u-sm-3">姓名</span>
                            <div class="one-obj-right am-u-sm-8">
                                <input type="text" name="username" class="">
                            </div>
                        </div>
                        <div class="one-obj-info am-g">
                            <span class="one-obj-left am-u-sm-3">手机号</span>
                            <div class="one-obj-right am-u-sm-8">
                                <input type="text" name="phone" class="">
                            </div>
                        </div>
                        <div class="one-obj-info am-g">
                            <div class="am-u-sm-10 align-center">
                                <input type="button" class="btn-my" value="确认" onclick="fToCreateOrder(this)">
                                <a href="javascript: void(0)" class="cancel am-link-muted" onclick="fCloseShow(this)">取消</a>
                            </div>
                        </div>

                        <input type="hidden" name="projectid" value="${projectMap.project.id}">
                        <%-- 件数 --%>
                        <input type="hidden" name="num" value="1">
                        <%-- 价格 --%>
                        <input type="hidden" name="price" value="${projectMap.product.price}">
                        <%-- 尺码 --%>
                        <input type="hidden" name="size" value="">
                    </c:when>
                    <c:when test="${projectMap.project.type == 2}">

                    </c:when>
                    <c:otherwise>
                        <div class="one-obj-info am-g">
                            <span class="one-obj-left am-u-sm-3">件数</span>
                            <div class="one-obj-right am-u-sm-8">
                                <span class="num-reduce" onclick="fReduceNum(this)">
                                    <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/icon_pro_reduce.png" alt="">
                                </span>
                                            <span class="num-show">1</span>
                                                <%--<input type="number" class="form-control num-show" value="1" min="1" required>--%>
                                <span class="num-add" onclick="fAddNum(this)">
                                    <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/icon_pro_add.png" alt="">
                                </span>
                            </div>
                        </div>
                        <div class="one-obj-info am-g">
                            <span class="one-obj-left am-u-sm-3">尺码</span>
                            <div class="one-obj-right am-u-sm-8" id="product-size"></div>
                        </div>
                        <div class="one-obj-info am-g">
                            <div class="am-u-sm-10 align-center">
                                <input type="button" class="btn-my" value="确认" onclick="fToCreateOrder(this)">
                                <a href="javascript: void(0)" class="cancel am-link-muted" onclick="fCloseShow(this)">取消</a>
                            </div>
                        </div>

                        <input type="hidden" name="projectid" value="${projectMap.project.id}">
                        <%-- 件数 --%>
                        <input type="hidden" name="num" value="1">
                        <%-- 价格 --%>
                        <input type="hidden" name="price" value="${projectMap.product.price}">
                        <%-- 尺码 --%>
                        <input type="hidden" name="size" value="">
                    </c:otherwise>
                </c:choose>

        </form>
    </div>

    <%-- 底部操作栏 --%>
    <div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default next-step" id="">
        <span>
            <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/icon_close.png" alt="" onclick="history.go(-1)">
            <%--<span class="my-icon-cancel" onclick="history.go(-1)"></span>--%>
        </span>

        <span>
            <c:choose>
                <c:when test="${projectMap.project.status == 1}">
                    <button type="submit" class="btn btn-default btn-my-submit" onclick="fShowProductSize(${projectMap.product.id})">支持ta</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-default btn-my-submit cancel" onclick="javascript:void(0)">支持ta</button>
                </c:otherwise>
            </c:choose>
        </span>

        <%--<span class="align-right" data-am-navbar-share onclick="fOnShareTimeLine();">--%>
            <%--<img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/icon_share.png" alt="">--%>
            <%--&lt;%&ndash;<span class="my-icon-share"></span>&ndash;%&gt;--%>
        <%--</span>--%>

    </div>

</body>

<%@ include file="../common/footer-script.jsp"%>


<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<%-- 微信 jssdk --%>
<%--<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>--%>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/projectInfo.js"></script>
<%--<script src="dist/js/front/projectInfo.js"></script>--%>

<script>
    $(function() {
        var url = '<%=request.getAttribute("url")%>';

        // 接口权限配置
//        fWechatConfig(url) ;
    });

</script>

</html>