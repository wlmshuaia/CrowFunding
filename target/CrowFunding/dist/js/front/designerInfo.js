/**
 * 设计师详情 js 操作
 * Created by wengliemiao on 16/1/18.
 */
/**
 * 获取设计师详情
 * @param id
 * @param url
 */
function fGetDesigner(id, url) {
    var oD = CommonHandler.fGetObjInfoJson(id, url);

    // 头像
    var headimgurl = '';
    if(oD.info != null && oD.info.headimgurl != null) {
        if(oD.info.headimgurl.indexOf('http') > -1) {
            headimgurl = oD.info.headimgurl;
        } else {
            headimgurl = DOMAIN_FILE + oD.info.headimgurl;
        }
    } else {
        headimgurl = DOMAIN_FILE + 'dist/imgs/icon_person.png';
    }

    // 用户名
    var nickname = '';
    if(oD.info != null && oD.info.nickname != null) {
        nickname = oD.info.nickname;
    } else {
        nickname = '佚名';
    }

    // 标签
    var sLabel = '';
    $.each(oD.labels, function(lk, lv) {
        if(lk == oD.labels.length - 1) {
            sLabel += lv;
        } else {
            sLabel += lv + " / ";
        }
    });

    var sHtml = '<div class="avatar align-center">'+
            '<a href="javascript:void(0)">'+
                //'<img src="'+ headimgurl +'" alt="">'+
                '<img data-original="'+ headimgurl +'" alt="" class="lazy">'+
                '<div class="icon designer-mark"></div>'+
            '</a>'+
        '</div>'+
        '<span class="des-name align-center">' + nickname + '</span>'+
        '<span class="des-info align-center">作品 '+ oD.projectnum +' / 粉丝 '+ oD.focusernum +'</span>'+
        '<span class="des-info align-center">'+ sLabel +'</span>'+

        '<div class="over-con right"></div>';

    $("#designer-head").html(sHtml);
}

/**
 * 获取设计师作品
 * @param id
 * @param url
 */
//function fGetDesignProjects(id, url) {
//    var oPs = CommonHandler.fGetObjInfoJson(id, url);
//
//    var sHtml = CommonHandler.fGetProjectsHtml(oPs);
//    $("#designer-projects").html(sHtml);
//}

