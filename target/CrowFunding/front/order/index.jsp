<%@ page import="com.tide.utils.DataUtils" %>
<%@ page import="com.tide.wechat.WxPayApi" %>
<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 15/12/22
  Time: 下午1:58
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

    <title>提交订单</title>

    <!-- Bootstrap core CSS -->
    <%--<link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <%--<link href="http://cdn.amazeui.org/amazeui/2.5.0/css/amazeui.min.css" rel="stylesheet">--%>

    <!-- Custom styles for this template -->
    <link href="<%=DataUtils.DOMAIN_FILE%>/dist/css/front/order-index.css" rel="stylesheet">
    <%--<link href="dist/css/front/order-index.css" rel="stylesheet">--%>

</head>
<body>
<%
//    out.println("userid: " + request.getAttribute("userid"));
//    out.println("username: " + request.getAttribute("username"));

    // 1.获取 openid
    String openid = "";
    if(request.getAttribute("error") != null) {
        out.print("error: " + request.getAttribute("error").toString());
    } else {
        Object oOpenid = request.getAttribute("openid");
        if(oOpenid != null) {
            openid = oOpenid.toString();
//            out.println("openid: " + openid);
        }
    }

    // 2.共享收货地址
    String sEditAddress = "";
    WxPayApi wxPayApi = new WxPayApi();
    if(request.getAttribute("access_token") != null) {
        String sAccessToken = request.getAttribute("access_token").toString();
//        out.println("access_token: " + sAccessToken);

        String url = request.getAttribute("url").toString();
        sEditAddress = wxPayApi.getEditAddressParameters(sAccessToken, url, request);
    }

%>

<script>
    /**
     * 调用共享收货地址接口
     */
    function fToEditAddress() {
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', editAddress, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', editAddress);
                document.attachEvent('onWeixinJSBridgeReady', editAddress);
            }
        }else{
            editAddress();
        }
    }

    /**
     * 获取共享地址
     */
    function editAddress() {
        WeixinJSBridge.invoke(
                'editAddress',
                <%
                    out.print(sEditAddress);
                %>
                ,
            function(res){
                var sHtml = '';
                if(res.err_msg == "edit_address:ok") { // 编辑地址成功
                    fGetAddressSuccess(res);
                } else { // 编辑地址失败
//                    fGetAddressFail();
                }
            }
        );
    }

    /**
     * 获取地址成功
     * @param res
     */
    function fGetAddressSuccess(res) {
        var sUserName = res.userName;   // 收货人姓名: 妈蛋文档写的是username
        var sTelNumber = res.telNumber; // 收货人电话
        var sProvince = res.proviceFirstStageName;          // 一级收货地址: 省
        var sCity = res.addressCitySecondStageName;         // 二级收货地址: 市
        var sCounties = res.addressCountiesThirdStageName;  // 三级收货地址: 区
        var sStreet = res.addressDetailInfo;                // 四级收货地址: 街道
        var sDetail = sProvince + sCity + sCounties + sStreet;

        // 设置地址相关 input 项
        $("input[name=username]").val(sUserName);
        $("input[name=phone]").val(sTelNumber);
        $("input[name=province]").val(sProvince);
        $("input[name=city]").val(sCity);
        $("input[name=counties]").val(sCounties);
        $("input[name=address]").val(sStreet);

        var sHtml = '<div class="address-info">' +
                    '<div class="address-con">' +
                        '<span class="username">收货人: ' + sUserName + '</span>' +
                        '<span class="tel-number">联系电话: ' + sTelNumber + '</span>' +
                    '<span class="address-detail">收货地址: ' + sDetail + '</span>' +
                    '</div>' +
                    '<div class="choose-address-con">' +
                        '<div class="choose-address">' +
                            '<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>' +
                        '</div>' +
                    '</div>' +
                '</div>';
        fSetAddressInfo(sHtml);

        // 设置高度
        var iHeight = document.getElementsByClassName("address-con")[0].offsetHeight;
        document.getElementsByClassName("choose-address-con")[0].style.height = iHeight - 20;
    }

    /**
     * 获取地址失败
     * @param res
     */
    function fGetAddressFail() {
        var sHtml = '<div class="address-toadd">' +
                '<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>' +
                '添加地址' +
                '</div>';
        fSetAddressInfo(sHtml);
    }

    /**
     * 设置地址信息
     * @param sHtml
     */
    function fSetAddressInfo(sHtml) {
        document.getElementById("addAddress").innerHTML = sHtml;
    }

</script>

<div class="main">
    <div class="add-address" id="addAddress" onclick="fToEditAddress();">
        <div class="address-toadd">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            添加地址
        </div>

        <%--<div class="address-info">--%>
            <%--<div class="address-con">--%>
                <%--<span class="username">收货人: 翁列苗</span>--%>
                <%--<span class="tel-number">联系电话: 15757126321</span>--%>
                <%--<span class="address-detail">收货地址: 浙江省杭州市江干区浙江理工大学浙江省杭州市江干区浙江理工大学浙江省杭州市江干区浙江理工大学</span>--%>
            <%--</div>--%>
            <%--<div class="choose-address-con">--%>
                <%--<div class="choose-address">--%>
                    <%--<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>

    <div class="product-list-con">
        <div class="product-list-title">
            <ul>
                <li class="wid-15">选择</li>
                <li>款式</li>
                <li>尺码</li>
                <li>数量</li>
                <li class="wid-25">小计(元)</li>
            </ul>
        </div>
        <form action="" method="post" id="orderForm">
            <div class="product-list">
                <div class="product-info">
                    <div class="p-obj wid-15">
                        <%--<input type="checkbox" class="checkbox-pro">--%>
                        <div class="checkbox-pro-con">
                            <input type="checkbox" value="1" id="checkboxProInput-1" name="" />
                            <label for="checkboxProInput-1"></label>
                        </div>
                    </div>
                    <div class="p-obj">
                        <c:choose>
                            <c:when test="${orderMap.projectMap.designimg != null}">
                                <img src="<%=DataUtils.DOMAIN_FILE%>/${orderMap.projectMap.designimg}" alt="">
                            </c:when>
                            <c:otherwise>
                                <img src="<%=DataUtils.DOMAIN_FILE%>/dist/imgs/img.png" alt="">
                            </c:otherwise>
                        </c:choose>
                        <%--170g精梳棉--%>
                        <c:out value="${orderMap.product.proname}"></c:out>
                    </div>
                    <div class="p-obj size">
                        <%--XL(女)--%>
                        <c:out value="${orderMap.size}"></c:out>
                    </div>
                    <div class="p-obj">
                        <span class="num-show clearfix">
                            <%--1--%>
                            <c:out value="${orderMap.num}"></c:out>
                        </span>
                        <span class="num-reduce" onclick="fReduceNum(this)">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                        </span>
                        <span class="num-add" onclick="fAddNum(this)">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        </span>
                    </div>
                    <div class="p-obj wid-25">
                        ¥ <span class="subtotalPrice">${orderMap.price * orderMap.num}</span> <br>
                        <span class="glyphicon glyphicon-trash delete" aria-hidden="true"></span>
                    </div>

                    <%-- 单价 --%>
                    <input type="hidden" name="price" value="${orderMap.price}">

                    <%-- 地址 --%>
                    <input type="hidden" name="username" value="">
                    <input type="hidden" name="phone" value="">
                    <input type="hidden" name="province" value="">
                    <input type="hidden" name="city" value="">
                    <input type="hidden" name="counties" value="">
                    <input type="hidden" name="address" value="">

                    <%-- 项目 --%>
                    <input type="hidden" name="projectid" value="${sessionScope.orderMap.projectMap.project.id}">
                    <input type="hidden" name="num" value="${orderMap.num}">
                    <input type="hidden" name="size" value="${orderMap.size}">
                </div>
            </div>
        </form>
    </div>

    <div class="price-count-con">
        <span>包邮</span>
        <span class="right">
            总计: ¥ <span id="totalPrice">${orderMap.price * orderMap.num}</span>
        </span>
    </div>

</div>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/common-handler.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/layer/layer.js"></script>
<script>
    // jsapi 参数字符串
    var sJsapiParams = '';

    // 订单号
    var sOutTradeNo = '';

    /**
     * 调用 jsApiCall 函数
     */
    function callpay() {
        if($(".address-info") == null || $(".address-info") == undefined || $(".address-info").length == 0) {
            CommonHandler.fShowTips("请选择收货地址");
//            alert("请填写收货地址");
            return ;
        }

        // 获取 jsapi 参数
        sJsapiParams = fGetJsapiParams();

        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', jsApiCall);
                document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
            }
        }else{
            jsApiCall();
        }
    }

    /**
     * 获取jsapi相关参数
     */
    function fGetJsapiParams() {
        var openid = '<%=openid%>';
//        var openid = '123456';
        var totalPrice = $("#totalPrice").text();

        var aForm = CommonHandler.fGetFormSerializable($("#orderForm"));
        aForm['openid'] = openid;
        aForm['totalPrice'] = totalPrice;

//        alert(aForm['username'] + ", " + aForm['phone'] + ", " + aForm['address'] + ", " + aForm['projectid'] + ", " + aForm['price'] +
//                ", " +aForm['num']);

        if(openid != null && openid != '') {
            var sJsapiParamsData = '';
            $.ajax({
                url: 'Pay/getJsapiParams.do',
                method: 'post',
                async: false,
//                data: {openid: openid, totalPrice: totalPrice},
                data: JSON.stringify(aForm),
                contentType: 'application/json',
                success: function(data) {
                    if(data.error != null) {
                        alert("服务器繁忙");
                    } else {
                        sJsapiParamsData = data.jsapiParam;
                        sOutTradeNo = data.outTradeNo;
                    }
//                    alert(sOutTradeNo);
                },
                error: function(data) {
                    alert("fGetJsapiParams error: " + data);
                }
            });
            return sJsapiParamsData;
        } else {
            alert("openid 为空!");
            return "";
        }
    }

    /**
     * 调用微信 JS api 支付接口
     */
    function jsApiCall() {
        var oJsapiParams = JSON.parse(sJsapiParams);

//        alert(sJsapiParams);

        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId" : oJsapiParams.appId,       //公众号名称，由商户传入
                    "timeStamp": oJsapiParams.timeStamp,//时间戳，自1970年以来的秒数
                    "nonceStr" : oJsapiParams.nonceStr, //随机串
                    "package" : oJsapiParams.package,
                    "signType" : oJsapiParams.signType, //微信签名方式：
                    "paySign" : oJsapiParams.paySign    //微信签名
                },
                /**
                 * res.err_desc 返回值描述 get_brand_wcpay_request
                 * res.err_msg  返回值说明
                 *      ok      支付成功
                 *      cancel  用户取消支付
                 *      fail    支付失败
                 * @param res
                 */
                function(res){
                    if(res.err_msg == "get_brand_wcpay_request:ok") { // 支付成功, 跳转到支付成功页面
//                        alert("支付成功");
                        window.location.href = "http://www.zm-college.com";
                    } else { // 支付失败, 删除相关订单信息
                        fDeleteOrder();
//                        alert("失败: " + res.err_msg);
                    }
                }
        );
    }

    /**
     * 删除订单
     */
    function fDeleteOrder() {
        $.ajax({
            url: 'front/Order/deleteOrder.do',
            method: 'get',
            data: {outTradeNo: sOutTradeNo},
            success: function(data) {
                if("success" == data) {
//                    alert("已成功取消支付");
                }
            },
            error: function(data) {
//                alert("取消支付出错 " + data);
            }
        });
    }

</script>

<div class="pay-type">
    <ul>
        <li onclick="callpay();">
            <button type="button" class="btn btn-success btn-block btn-lg">微信支付</button>
        </li>
        <%--<li>支付宝支付</li>--%>
        <%--<li>找人代付</li>--%>
    </ul>
</div>
</body>

<%--<script src="dist/js/jquery-2.1.1.min.js"></script>--%>
<%--<script src="dist/js/bootstrap.min.js"></script>--%>

<%--<script src="http://cdn.amazeui.org/amazeui/2.5.0/js/amazeui.min.js"></script>--%>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="<%=DataUtils.DOMAIN_FILE%>/dist/js/front/order.js"></script>
<%--<script src="dist/js/front/order.js"></script>--%>

</html>
