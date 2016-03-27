<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="row">
    <div class="col-sm-3 col-md-2 sidebar" id="sidebar-left">

        <ul class="nav nav-sidebar">
            <li>
                <a href="admin/Index/index.do" class="nav-header menu-first collapsed">
                    <i class="glyphicon glyphicon-home icon"></i>
                    网站概况
                </a>
            </li>

            <li>
                <a href="#userManager" class="nav-header menu-first collapsed" data-toggle="collapse">
                    <i class="glyphicon glyphicon-user icon"></i>
                    用户管理
                    <span class="glyphicon glyphicon-chevron-right"></span>
                </a>
                <ul id="userManager" class="nav nav-list collapse menu-second" aria-expanded="true">
                    <li><a href="admin/User/selectAll.do">基本资料</a></li>
                    <li><a href="admin/error/develop.jsp">设计管理</a></li>
                </ul>
            </li>

            <li>
                <a href="#pageManager" class="nav-header menu-first collapsed" data-toggle="collapse">
                    <i class="glyphicon glyphicon-file icon"></i>
                    首页管理
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <%--<span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>--%>
                </a>
                <%--<ul id="pageManager" class="nav nav-list collapse menu-second in">--%>
                <ul id="pageManager" class="nav nav-list collapse menu-second">
                    <li><a href="admin/Page/selectAll.do">首页轮播图</a></li>
                    <li><a href="admin/page/addPage.jsp">添加轮播图</a></li>
                    <li><a href="admin/page/hotLabels.jsp">热门标签</a></li>
                </ul>
            </li>

            <li>
                <a href="#themeManager" class="nav-header menu-first collapsed" data-toggle="collapse">
                    <i class="glyphicon glyphicon-th icon"></i>
                    主题管理
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
                <ul id="themeManager" class="nav nav-list collapse menu-second">
                    <li><a href="admin/Theme/selectAll.do">全部主题</a></li>
                    <li><a href="admin/Theme/addTheme.do">添加主题</a></li>
                </ul>
            </li>

            <li>
                <a href="#label" class="nav-header menu-first collapsed" data-toggle="collapse">
                    <i class="icon icon-project">&#xe6a2;</i>
                    项目管理
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
                <ul id="label" class="nav nav-list collapse menu-second">
                    <li><a href="admin/Project/selectAll.do">全部项目</a></li>
                    <li><a href="admin/Label/selectAll.do">标签管理</a></li>
                </ul>
            </li>

            <li>
                <a href="admin/Order/selectAll.do" class="nav-header menu-first collapsed">
                    <i class="icon icon-order">&#xe604;</i>
                    订单管理
                </a>
            </li>

            <li>
                <a href="#product" class="nav-header menu-first collapsed" data-toggle="collapse">
                    <i class="glyphicon glyphicon-shopping-cart"></i>
                    商品管理
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
                <ul id="product" class="nav nav-list collapse menu-second">
                    <li><a href="admin/Product/selectAll.do">全部商品</a></li>
                    <li><a href="admin/Product/addProduct.do">添加商品</a></li>
                    <li><a href="admin/Product/cateList.do">设置分类</a></li>
                    <li><a href="admin/Product/selectProAttr.do">设置属性</a></li>
                </ul>
            </li>

            <li>
                <a href="#msgCenter" class="nav-header menu-first collapsed" data-toggle="collapse">
                    <i class="glyphicon glyphicon-envelope icon"></i>
                    消息中心
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
                <ul id="msgCenter" class="nav nav-list collapse menu-second">
                    <li><a href="admin/Comment/selectAll.do">项目热议</a></li>
                    <li><a href="admin/error/develop.jsp">发送站内信</a></li>
                </ul>
            </li>

            <li>
                <a href="#design" class="nav-header menu-first collapsed" data-toggle="collapse">
                    <i class="glyphicon glyphicon-info-sign icon"></i>
                    定制属性
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
                <ul id="design" class="nav nav-list collapse menu-second">
                    <li><a href="">全部主题</a></li>
                    <li><a href="#">文字预设</a></li>
                    <li><a href="#">笔头形状</a></li>
                    <li><a href="#">笔头颜色</a></li>
                </ul>
            </li>

            <li>
                <a href="#articleMenu" class="nav-header menu-first collapsed" data-toggle="collapse">
                    <i class="glyphicon glyphicon-certificate"></i>
                    权限管理
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
                <ul id="articleMenu" class="nav nav-list collapse menu-second">
                    <li><a href="admin/Role/selectAll.do">角色</a></li>
                    <li><a href="admin/Node/selectAll.do">权限</a></li>
                    <li><a href="admin/Admin/selectAll.do">后台用户</a></li>
                </ul>
            </li>

        </ul>

    </div>

</div>
