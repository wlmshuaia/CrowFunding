/**
 * 后台首页相关js操作
 * Created by wengliemiao on 15/12/29.
 */

$(function() {
    // 进入页面显示左侧菜单
    var sMenuName = $(".breadcrumb li").last().text();
    fSetChooseMenu(sMenuName);

    // 绑定点击菜单事件
    $("#sidebar-left").find(".menu-first").bind("click", fShowSubMenu);
});

/**
 * 点击展开子菜单
 */
function fShowSubMenu() {
    // 估计bootstrap的操作较本函数先
    if(!$(this).hasClass("collapsed")) {
        $(this).find("span").attr("class", "glyphicon glyphicon-chevron-right");
    } else {
        $(this).find("span").attr("class", "glyphicon glyphicon-chevron-down");
    }
}

/**
 * 设置选中菜单项
 * @param sMenuName
 * @returns {boolean}
 */
function fSetChooseMenu(sMenuName) {
    var aMenu = $("#sidebar-left").find("a");
    for (var i = 0; i < aMenu.length; i ++) {
        // 链接地址
        var sName = $(aMenu[i]).text();
        sName = $.trim(sName);
        if(sName == sMenuName || sName.indexOf(sMenuName) > 0) { // 选中状态
            //console.log("sName2:"+sName);
            if(!$(aMenu[i]).hasClass("menu-first")) { // 二级菜单
                var oUl = $(aMenu[i]).parents("ul").eq(0);
                var oMenuFir = oUl.siblings("a").eq(0);
                $(oMenuFir).removeClass("collapsed");
                $(oMenuFir).find("span").attr("class", "glyphicon glyphicon-chevron-down");

                $(oUl).addClass("in");
            }
            $(aMenu[i]).parent("li").addClass("active");
            return false;
        }
    }
}
