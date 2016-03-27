<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/11/29
  Time: 下午5:21
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

    <title>众筹后台管理系统</title>

    <!-- 公共样式 -->
    <%@ include file="./common/header-link.jsp" %>

    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/admin/index.css" rel="stylesheet">

</head>
<body>
<%@ include file="common/header.jsp" %>

<div class="admin-main">
    <div class="container-fluid">
        <%@ include file="common/nav.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h2 class="page-header">网站概况</h2>

            <ol class="breadcrumb un-display">
                <li>网站概况</li>
            </ol>

            <div class="row index-overview">

                <div class="col-xs-6 col-sm-3">
                    <div class="panel purple">
                        <div class="symbol">
                            <i class="glyphicon glyphicon-user"></i>
                        </div>
                        <div class="state-value">
                            <div class="value">${infoMap.userCount}</div>
                            <div class="title">
                                <a href="admin/User/selectAll.do">用户量</a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xs-6 col-sm-3">
                    <div class="panel red">
                        <div class="symbol">
                            <i class="icon icon-project">&#xe6a2;</i>
                        </div>
                        <div class="state-value">
                            <div class="value">${infoMap.projectCount}</div>
                            <div class="title">
                                <a href="admin/Project/selectAll.do">项目数</a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xs-6 col-sm-3">
                    <div class="panel blue">
                        <div class="symbol">
                            <i class="icon icon-order">&#xe604;</i>
                        </div>
                        <div class="state-value">
                            <div class="value">${infoMap.orderCount}</div>
                            <div class="title">
                                <a href="admin/Order/selectAll.do">订单数</a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xs-6 col-sm-3">
                    <div class="panel green">
                        <div class="symbol">
                            <i class="glyphicon glyphicon-eye-open"></i>
                        </div>
                        <div class="state-value">
                            <div class="value">100</div>
                            <div class="title">访问量</div>
                        </div>
                    </div>
                </div>

            </div>

            <h2 class="sub-header">访问量统计</h2>

            <div class="table-responsive">
                <div id="container" style="width: 95%;"></div>
            </div>
        </div>
    </div>
</div>

<%@ include file="./common/footer.jsp" %>

<%--<script src="assets/js/vendor/holder.js"></script>--%>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/js/vendor/highcharts.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/js/vendor/exporting.js"></script>
<script>
    $(function () {
        $('#container').highcharts({
            title: {
                text: '月访问量统计',
                x: -20 //center
            },
            subtitle: {
// 		            text: 'Source: WorldClimate.com',
// 		            x: -20
            },
            xAxis: {
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                    'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
            },
            yAxis: {
                title: {
                    text: '次数'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: ''
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                name: '用户量',
                data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
            }, {
                name: '点击量',
                data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
            },]
        });

    });

</script>
</body>
</html>

