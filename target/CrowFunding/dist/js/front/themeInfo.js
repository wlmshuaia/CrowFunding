/**
 * 主题详情相关 js 操作
 * Created by wengliemiao on 16/1/16.
 */
$(function() {

});

function fGetThemeInfo(id, url) {
    var oTheme = CommonHandler.fGetObjInfoJson(id, url);

    // 主题名称
    $("#theme-title").text(oTheme.name);

    // 主题内容
    //var sHtml = CommonHandler.fGetProjectsHtml(tMap.projects);
    //$("#theme-project-con").html(sHtml);

    MyLoader.fLoad({id: id}, "front/Theme/getThemeDetail.do", "#theme-project-con");
}


