/**
 * 主题列表 js 操作
 * Created by wengliemiao on 16/1/16.
 */

$(function() {
    MyLoader.fLoad({'num': 4}, "front/Theme/getThemeList.do", "#theme-con", function(tMap) {
        var sHtml = '';
        $.each(tMap, function(k, v) {
            sHtml += ' <li>'+
                '<a href="front/theme/themeInfo.jsp?id='+ v.id +'">'+
                    //'<img class="am-thumbnail" src="'+ v.posterurl +'" />'+
                    '<img class="am-thumbnail lazy" data-original="'+ DOMAIN_FILE + v.posterurl +'" />'+
                '</a>'+
                '</li>';
        });
        return sHtml;
    });
});


