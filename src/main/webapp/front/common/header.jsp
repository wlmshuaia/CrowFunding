<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/5
  Time: 下午3:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 顶部栏 --%>
<div data-am-sticky>
    <header data-am-widget="header" class="am-header am-header-default header">
        <div class="am-header-left am-header-nav center am-dropdown" data-am-dropdown>
            <a href="javascript:void(0)" class="icon am-dropdown-toggle" data-am-dropdown-toggle></a>

            <ul class="am-dropdown-content">
                <li><a href="front/search/getByType.jsp?type=all">全部</a></li>
                <li><a href="front/search/getByType.jsp?type=newst">最新</a></li>
                <li><a href="front/search/getByType.jsp?type=hotst">最热</a></li>
                <li><a href="front/search/getByType.jsp?type=crowfunding">筹集中</a></li>
                <li><a href="front/search/getByType.jsp?type=success">已达成</a></li>
            </ul>

        </div>

        <h1 class="am-header-title">
            <a href="javascript:void(0)" class="">
                <%--潮客公园--%>
                众梦空间
            </a>
        </h1>

        <%-- 搜索按钮 --%>
        <div class="am-header-right am-header-nav">
            <a href="javascript:void(0)" class="" onclick="fShowSearch()">
                <span class="icon head-search-btn"></span>
            </a>
        </div>

        <%-- 搜索容器 --%>
        <div class="search-con-cover"></div>
        <div class="search-con">

            <%-- 搜索框 --%>
            <div class="s-con">
                <div class="one-content">
                    <div class="am-g search-input-con">
                        <div class="am-u-sm-10">
                            <input type="text" name="searchcon" class="am-form-field search-input" placeholder="搜索">
                        </div>
                        <div class="am-u-sm-2">
                            <span class="icon search-btn" onclick="fToSearch(this)"></span>
                        </div>
                    </div>
                </div>
            </div>

            <%-- 缓冲动画 --%>
            <div class="s-con my-loader-con am-vertical-align-middle">
                <div class="my-loader">
                    <i class="am-icon-spinner am-icon-spin"></i>
                </div>
            </div>

            <div id="hot-con"></div>

            <div class="close-con align-right">
                <a href="javascript:void(0)" class="btn-my" onclick="fToSearch(this);">确定</a>
                <a href="javascript:void(0)" class="btn-my-cancel">取消</a>
            </div>
        </div>

    </header>
</div>
