/**
 * 个人中心相关js操作
 * Created by wengliemiao on 16/1/12.
 */
$(function(){
    // 加载 我发布的-全部
    var aData = {'status': 'all', 'header': 'header-add'};
    fSetContent($(".p-con-box").eq(0).find(".am-thumbnails"), aData);

    // 一级选项卡点击切换
    $(".personal-header li").bind("click", fSwitchHeaderTab);

    // 状态选项卡切换
    $(".p-header li").bind("click", fSwitchStatusTab);

    // 类型选项卡切换
    $(".t-header li").bind("click", fSwitchTypeTab);
});

// 一级选项卡切换: 我发布的, 我支持的, 我关注的
function fSwitchHeaderTab() {
    var index = $(this).index();

    // 设置选中样式
    $(".personal-header li span").removeClass("f-active");
    $(this).find("span").addClass("f-active");

    // 显示选中区域
    $(".p-obj").hide();
    $(".p-obj").eq(index).fadeIn();

    // 容器
    var oCon = $(this).parents(".main").find(".p-obj").eq(index).find(".p-con-box").eq(0).find(".am-thumbnails");
    var sObjHtml = $(oCon).html();
    if(sObjHtml == '' || sObjHtml == undefined || sObjHtml.indexOf("am-icon-spinner") > 0 || sObjHtml.indexOf("去逛逛") > 0) {
        // 加载第一个内容
        var aData = {};
        aData['status'] = 'all';
        aData['header'] = $(this).text();

        // 显示缓冲动画
        $(oCon).html('<div class="my-loader align-center">'+
            '<i class="am-icon-spinner am-icon-spin"></i>'+
            '<p>加载中...</p>'+
            '</div>');

        fSetContent($(oCon), aData);
    }
}

// 状态选项卡切换: 全部, 进行中, 已达成
function fSwitchStatusTab() {
    var index = $(this).index();

    var obj = $(this).parents(".p-header");

    // 设置选中样式
    $(obj).find("li").removeClass("s-active");
    $(obj).find("li").eq(index).addClass("s-active");

    // 显示选中区域
    $(obj).siblings(".p-con-box").hide();
    var oObjCon = $(obj).siblings(".p-con-box").eq(index);
    $(oObjCon).fadeIn();

    var sObjHtml = $(oObjCon).find(".am-thumbnails").html();
    if(sObjHtml == '' || sObjHtml == undefined || sObjHtml.indexOf("am-icon-spinner") > 0 || sObjHtml.indexOf("去逛逛") > 0) {
        // 显示缓冲动画
        $(oObjCon).find(".am-thumbnails").html('<div class="my-loader align-center">'+
            '<i class="am-icon-spinner am-icon-spin"></i>'+
            '<p>加载中...</p>'+
            '</div>');

        // 拼接查询参数
        var aData = {};
        aData['status'] = $(this).text();
        aData['header'] = $(".personal-header").find(".f-active").text();

        var oTHeader = $(this).parents(".p-obj-box").siblings(".t-header");
        if(oTHeader != null) {
            aData['type'] = $(oTHeader).find(".t-active").text();
        }

        fSetContent($(oObjCon).find(".am-thumbnails"), aData);
    }
}

/**
 * 设置 html 内容
 * @param obj
 * @param map
 */
function fSetContent(obj, aData) {
    aData = fChangeParam(aData);
    //console.log(aData);

    MyLoader.fLoad(aData, "front/User/getInfoByJson.do", $(obj), function(map){
        var sHtml = '';
        if(map != null && map.length > 0) {
            sHtml = CommonHandler.fGetProjectsHtml(map);
        } else { // 数据为空
            sHtml = '<div class="align-center am-vertical-align h-80">'+
                        '<div class="am-vertical-align-middle">'+
                            '<img src="'+ DOMAIN_FILE +'dist/imgs/icon_none.png" width=76 height=77 alt="">'+
                            '<div class="p-gray">'+
                                '<p>暂时没有相关的信息哟!</p>'+
                            '</div>'+
                            '<a href="index.jsp" class="btn-my a">去逛逛</a>'+
                        '</div>'+
                    '</div>';
        }
        return sHtml;
    });

    var oCon = $(obj).parents(".p-con-box").find("li").find(".p-con");
    var oImg = $("li").find(".p-con");
    var aImg = $(oImg).find("img");
    $(aImg).css("height", $(oCon).width() + "px");
}

// 类型选项卡切换: 项目, 设计师
function fSwitchTypeTab() {
    var index = $(this).index();
    var obj = $(this).parents(".p-obj");

    // 设置选中样式
    $(obj).find(".t-header li").removeClass("t-active");
    $(obj).find(".t-header li").eq(index).addClass("t-active");

    if(index == 1) { // 点击加载关注的设计师
        var oCon = $(this).parents(".p-obj").find(".p-obj-box").eq(index).find(".am-thumbnails");
        $(oCon).html('<div class="my-loader align-center">'+
                '<i class="am-icon-spinner am-icon-spin"></i>'+
                '<p>加载中...</p>'+
            '</div>');

        // 拼接参数
        var aData = {};
        aData['status'] = '';
        aData['type'] = $(this).text();
        aData['header'] = $(".personal-header").find(".f-active").text();
        aData = fChangeParam(aData);
        aData['num'] = 4; // 默认加载8条数据
        // 加载更多
        MyLoader.fLoad(aData, "front/User/getInfoByJson.do", $(oCon), fGetDesignerHtml);
    }

    // 显示选中区域
    $(obj).find(".p-obj-box").hide();
    $(obj).find(".p-obj-box").eq(index).fadeIn();
}

/**
 * 转换参数: 中英文转换
 * @param aData
 * @returns aData
 */
function fChangeParam(aData) {
    var sHeader = "";
    // 转换一级选项卡参数
    if(aData['header'] != null) {
        switch (aData['header']) {
            case '我发布的':
                sHeader = 'header-add';
                break;
            case '我支持的':
                sHeader = 'header-back';
                break;
            case '我关注的':
                sHeader = 'header-focus';
                break;
            default:
                sHeader = 'header-add';
                break;
        }
        aData['header'] = sHeader;
    }

    // 转换项目状态
    if(aData['status'] != null) {
        sHeader = "";
        switch (aData['status']) {
            case '全部':
                sHeader = 'all';
                break;
            case '进行中':
                sHeader = 'crowfunding';
                break;
            case '未达成':
                sHeader = 'fail';
                break;
            case '已达成':
                sHeader = 'success';
                break;
            default:
                sHeader = 'all';
                break;
        }
        aData['status'] = sHeader;
    }

    // 转换用户类型
    if(aData['type'] != null) {
        sHeader = "";
        switch (aData['type']) {
            case '设计师':
                sHeader = 'type-designer';
                break;
            case '项目':
                sHeader = 'type-project';
                break;
            default:
                sHeader = 'type-project';
                break;
        }
        aData['type'] = sHeader;
    }

    return aData;
}

