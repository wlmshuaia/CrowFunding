/**
 * 项目详情页相关js
 * Created by wengliemiao on 15/12/22.
 */

$(function() {
    // oninput 为html5标准事件, onpropertychange 为IE9以下支持事件
    $(".num-show").bind("input propertychange", CommonHandler.fChangeNum);
    $(".num-show").blur(fCheckNum);

    // 设置图片高度和宽度一致
    var oImg = $(".p-img").find("img");
    oImg.css("height", $(".p-img").width() + "px");
});

// 微信相关 js 接口调用
//wx.ready(function() {
//    /**
//     * 判断当前客户端版本是否支持分享参数自定义
//     */
//    wx.checkJsApi({
//        jsApiList: [
//            'getLocation',
//            'onMenuShareTimeline',
//            'onMenuShareAppMessage'
//        ],
//        success: function (res) {
//            alert(JSON.stringify(res));
//        }
//    });
//
//    /**
//     * 分享给好友
//     */
//    wx.onMenuShareAppMessage({
//        title: 'hhh', // 分享标题
//        desc: '分享给朋友测试', // 分享描述
//        link: 'http://www.zm-college.com/front/Project/projectInfo.do?id=43', // 分享链接
//        imgUrl: 'http://file.wenglm.cn/dist/imgs/icon_share.png', // 分享图标
//        //type: '', // 分享类型,music、video或link，不填默认为link
//        //dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
//        trigger: function() {
//            alert("用户点击发送给朋友");
//        },
//        success: function () {
//            // 用户确认分享后执行的回调函数
//            alert("成功分享");
//        },
//        cancel: function () {
//            // 用户取消分享后执行的回调函数
//            alert("取消分享");
//        }
//    });
//
//    /**
//     * 分享到朋友圈
//     */
//    wx.onMenuShareTimeline({
//        title: '分享到朋友圈测试', // 分享标题
//        link: 'http://www.zm-college.com/front/Project/projectInfo.do?id=43', // 分享链接
//        imgUrl: 'http://file.wenglm.cn/dist/imgs/icon_share.png', // 分享图标
//        trigger: function(data) {
//            alert("用户点击分享到朋友圈");
//        },
//        success: function (data) {
//            // 用户确认分享后执行的回调函数
//            alert("已分享");
//        },
//        cancel: function () {
//            // 用户取消分享后执行的回调函数
//            alert("已取消");
//        }
//    });
//});

/**
 * 通过config接口注入权限验证配置
 */
//function fWechatConfig(url) {
//    $.ajax({
//        url: 'Pay/getConfigParams.do',
//        data: {url: url},
//        success: function(data) {
//            // 注入权限验证配置
//            wx.config({
//                debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
//                appId: data.appid, // 必填，公众号的唯一标识
//                timestamp: data.timestamp, // 必填，生成签名的时间戳
//                nonceStr: data.noncestr, // 必填，生成签名的随机串
//                signature: data.sign,// 必填，签名，见附录1
//                jsApiList: [ // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
//                    'checkJsApi',
//                    'onMenuShareTimeline',
//                    'onMenuShareAppMessage'
//                ]
//            });
//        },
//        error: function(data) {
//            alert("config error");
//        }
//    });
//}

/**
 * 显示尺码信息
 * @param id
 */
function fShowProductSize(id) {
    var pMap = CommonHandler.fGetObjInfoJson(id, "front/Product/getObjSizeInfo.do");

    // 价格
    var sHtml = '<span class="left">'+ pMap.product.proname +'</span>'+
        '<span class="right">¥ <span id="totalPrice">'+ pMap.product.price +'</span>'+
        '<input type="hidden" name="pro-price" value="'+ pMap.product.price +'"'+
        '</span>';
    $("#product-info").html(sHtml);

    // 尺码
    sHtml = '<ul>' ;
    $.each(pMap.psList, function(k, v) {
        sHtml += '<li onclick="fChooseSize(this)">'+ v.size +'</li>';
    });
    sHtml += '</ul>';
    $("#product-size").html(sHtml);

    $("#showInfo").fadeIn();
}

/**
 * 选择尺码操作
 * @param obj
 */
function fChooseSize(obj) {
    $("#product-size li").removeClass("bg-success");
    $(obj).addClass("bg-success");
    $(obj).parents("form").find("input[name=size]").val($(obj).text());
}

/**
 * 查看输入框值
 * @param e
 */
function fCheckNum(e) {
    var iNum = parseInt($(this).val());
    if(isNaN(iNum) || iNum < 1) {
        $(this).val(1);
        var iPrice = parseInt($("input[name=pro-price]").val());
        // 显示总价
        $("#totalPrice").text(iPrice);
    }
}

/**
 * 隐藏信息框
 * @param obj
 */
function fCloseShow(obj) {
    $("#showInfo").fadeOut();

    var oForm = $(obj).parents("form");
    $(oForm).find("input[name=num]").val("1");
    $(oForm).find(".num-show").text("1");
    $(oForm).find("input[name=size]").val("");
}

/**
 * 提交订单
 *  1.判断参数
 *  2.ajax提交
 * @param obj
 */
function fToCreateOrder(obj) {
    var oForm = $(obj).parents("form");

    if($(oForm).find("input[name=size]").val() == "") {
        CommonHandler.fShowTips("请选择尺码!");
    } else {
        $(oForm).attr("action", "front/Order/index.do");
        $(oForm).submit();
    }
}

/**
 * 减少数量
 * @param obj
 */
function fReduceNum(obj) {
    var oNum = $(obj).siblings(".num-show");
    var iNum = parseInt(oNum.text());

    if (iNum > 1) {
        $(oNum).text(iNum - 1);
        $(obj).parents("form").find("input[name=num]").val(iNum - 1);
    }
}

/**
 * 增加数量
 * @param obj
 */
function fAddNum(obj) {
    var oNum = $(obj).siblings(".num-show");
    var iNum = parseInt(oNum.text());

    $(oNum).text(iNum + 1);
    $(obj).parents("form").find("input[name=num]").val(iNum + 1);
}

