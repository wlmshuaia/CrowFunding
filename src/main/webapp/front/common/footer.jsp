<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/5
  Time: 下午3:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 底部栏 --%>
<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default footer" id="">
    <ul class="am-navbar-nav am-cf am-avg-sm-4">
        <li>
            <a href="index.jsp" class="">
                <span class="icon icon-home"></span>
                <span class="am-navbar-label">主页</span>
            </a>
        </li>
        <li>
            <a href="front/theme/index.jsp" class="">
                <span class="icon icon-theme"></span>
                <span class="am-navbar-label">主题</span>
            </a>
        </li>
        <li>
            <%--<a href="front/Project/addProject.do" class="">--%>
            <a href="javascript:void(0)" class="" onclick="fShowType()">
                <span class="icon icon-add"></span>
                <span class="am-navbar-label">发布</span>
            </a>
        </li>
        <li>
            <a href="front/designer/index.jsp" class="">
                <span class="icon icon-designer"></span>
                <span class="am-navbar-label">设计师</span>
            </a>
        </li>
        <li>
            <a href="front/user/index.jsp" class="">
                <span class="icon icon-personal"></span>
                <span class="am-navbar-label">我</span>
            </a>
        </li>
    </ul>
</div>
