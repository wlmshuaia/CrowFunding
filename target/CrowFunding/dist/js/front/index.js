/**
 * 首页相关js操作
 * Created by wengliemiao on 16/1/8.
 */

$(function() {
    // 初始化首页
    fInitIndex();
});

/**
 * 初始化首页
 */
function fInitIndex() {
    fLoadSlide("front/Index/getIndexSlide.do");
    fLoadTheme("front/Index/getIndexTheme.do");
    fLoadProject("front/Index/getIndexProject.do");
    //MyLoader.fLoad({}, "front/Index/getIndexProject.do", "#wrapper");
}

/**
 * 加载轮播图
 * @param url
 */
function fLoadSlide(url) {
    var tMap = CommonHandler.fGetInfoByJson(null, url);

    var sHtml = '';
    $.each(tMap, function(k, v) {
        sHtml += '<li>'+
                '<div class="item">'+
                    '<a href="'+ v.linkurl +'">'+
                        '<img src="'+ DOMAIN_FILE + v.posterurl +'" alt="" class="lazy">'+
                        //'<img data-original="'+ v.posterurl +'" class="lazy">'+
                    '</a>'+
                '</div>'+
            '</li>';
    });

    $("#slide-con-index").html(sHtml);
    $('.am-slider').flexslider();

    // 设置宽高比例 2 : 1
    var aImg = $("#index-slider").find("img");
    //$(aImg).css("height", $("#index-slider").width() / 2 + "px");
    //alert("width hhh: " + $("#index-slider").width());
    $(aImg).css("height", $("#index-slider").width() / 2 + "px");
}

/**
 * 加载主题
 * @param url
 */
function fLoadTheme(url) {
    var tMap = CommonHandler.fGetInfoByJson(null, url);

    var sHeadHtml = '';
    var sHtml = '';
    if(tMap.length == 0) {
        sHeadHtml = '热门主题';
        sHtml = '暂无主题';
    } else {
        // 主题名称
        sHeadHtml = '<span><h4>'+ tMap[0].theme.name +'</h4></span>'+
            '<span class="right more"><a href="front/theme/themeInfo.jsp?id='+ tMap[0].theme.id +'">更多</a></span>';

        // 主题项目内容

        $.each(tMap[0].pMapList, function(k, v) {

            // 项目效果图
            var designimg = '';
            if(v.designimg != null && v.designimg[0] != undefined) {
                designimg = v.designimg[0].projectimg;
            } else {
                designimg = 'dist/imgs/img.png';
            }
            // 加上域名
            designimg = DOMAIN_FILE + designimg;

            // 是否关注
            var sFocus = '';
            if("1" == v.ifFocus) {
                sFocus = '<div class="over-con-on right" onclick="CommonHandler.fCancelFocusProject(this, '+ v.project.id +');"></div>';
            } else {
                sFocus = '<div class="over-con right" onclick="CommonHandler.fFocusProject(this, '+ v.project.id +');"></div>';
            }

            sHtml += '<td>'+
                '<div class="p-con">'+
                    '<div class="p-img">'+
                        '<a href="front/Project/projectInfo.do?id='+ v.project.id +'">'+
                            //'<img src="'+ designimg +'" alt="">'+
                            '<img data-original="'+ designimg +'" alt="" class="lazy">'+
                        '</a>'+
                    '</div>'+
                    '<div class="p-info">'+
                        '<div class="progress">'+
                            '<div class="am-progress-bar am-progress-bar-warning" style="width: '+ v.percent +'%;"></div>'+
                        '</div>'+
                        '<span class="title am-text-truncate">'+ v.project.title +'</span>'+sFocus +
                    '</div>'+
                '</div>'+
                '</td>';
        });

        $("#theme-con-header-index").html(sHeadHtml);
        $("#theme-con-index").html(sHtml);
    }
    $("#slider-theme").hide();
}

/**
 * 加载逛一逛
 * @param url
 */
function fLoadProject(url) {
    //var tMap = CommonHandler.fGetInfoByJson(null, url);
    //var sHtml = CommonHandler.fGetProjectsHtml(tMap);
    //$("#project-con-index").html(sHtml);
    MyLoader.fLoad({}, url, "#project-con-index", null);
}




