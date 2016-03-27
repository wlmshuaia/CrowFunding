<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div class="admin-header">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <%--<a class="navbar-brand" href="javascript:void(0)">体检预约后台管理系统</a>--%>
                <span class="navbar-brand">众筹后台管理系统</span>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <%--<li><a href="#">${sessionScope.adminname}</a></li>--%>
                    <li>
                        <a href="admin/Admin/personal.do?adminid=${sessionScope.adminid}">
                            <%--<i class="icon icon-person">&#xe602;</i> 个人中心</a>--%>
                            <i class="icon icon-person">&#xe602;</i> ${sessionScope.adminname}
                        </a>
                    </li>
                        <li>
                            <a href="admin/Login/logout.do">
                                <i class="icon icon-logout">&#xe62c;</i>
                                退出
                            </a>
                        </li>
                </ul>

            </div>
        </div>
    </nav>
</div>

