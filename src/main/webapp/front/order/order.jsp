<%@ page import="com.tide.wechat.WxPayApi" %><%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/3/2
  Time: 下午10:20
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

    <title>Title</title>

</head>
<body>

<%
    WxPayApi wTools = new WxPayApi();
    System.out.println(request.getRequestURI());
    System.out.println(request.getRequestURL());
    String url = request.getAttribute("url").toString();
    wTools.getEditAddressParameters("123", url, request);
%>

</body>
</html>
