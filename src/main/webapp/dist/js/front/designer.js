/**
 * 设计师列表页面 js 操作
 * Created by wengliemiao on 16/1/17.
 */

// 文件域名
//var DOMAIN_FILE = '<%=DataUtils.DOMAIN_FILE%>/';

/**
 * 加载设计师列表
 * @param url
 */
function fInit(url) {

}

/**
 * 获取设计师html内容
 * @param tMap
 */
function fGetDesignerHtml(tMap) {
    var sHtml = '';
    $.each(tMap, function (dk, dv) {
        // 头像
        var headimgurl = '';
        if (dv.designer != undefined && dv.designer.info.headimgurl != null) {
            if(dv.designer.info.headimgurl.indexOf('http') > -1) {
                headimgurl = dv.designer.info.headimgurl;
            } else {
                headimgurl = DOMAIN_FILE + dv.designer.info.headimgurl;
            }
        } else {
            headimgurl = DOMAIN_FILE + 'dist/imgs/icon_person.png';
        }

        // 昵称
        var nickname = '';
        if(dv.designer != undefined && dv.designer.info.nickname != null) {
            nickname = dv.designer.info.nickname;
        } else {
            nickname = '佚名';
        }

        // 标签
        var sLabel = '';
        $.each(dv.designer.labels, function(lk, lv) {
            if(lk == dv.designer.labels.length - 1) {
                sLabel += lv;
            } else {
                sLabel += lv + " / ";
            }
        });

        // 是否关注
        var sFocus = '';
        if("1" == dv.designer.ifFocus) {
            sFocus = '<div class="over-con-on right" onclick="CommonHandler.fCancelFocusUser(this, '+dv.designer.info.id+')"></div>';
        } else {
            sFocus = '<div class="over-con right" onclick="CommonHandler.fFocusUser(this, '+dv.designer.info.id+')"></div>';
        }

        sHtml += '<div class="one-con">' +
            '<div class="con-header">' +
            '<div class="avatar align-center">' +
            '<a href="front/designer/designerInfo.jsp?id='+ dv.designer.info.id +'">' +
                //'<img src="' + headimgurl + '" alt="">' +
                '<img data-original="' + headimgurl + '" alt="" class="lazy">' +
            '<div class="icon designer-mark"></div>' +
            '</a>' +
            '</div>' +
            '<span class="des-name align-center">' + nickname + '</span>' +
            '<span class="des-info align-center">作品 '+ dv.designer.projectnum +' / 粉丝 '+ dv.designer.focusernum +'</span>' +
            '<span class="des-info align-center">'+ sLabel +'</span>' + sFocus +
            '</div>' +
            '<div class="project-content">' +
            '<ul class="am-avg-sm-3 am-avg-md-4 am-avg-lg-5 am-thumbnails">';

        if (dv.projects.length > 0) {
            $.each(dv.projects, function (k, v) {
                var designimg = '';
                if (v.designimg != undefined) {
                    designimg = DOMAIN_FILE + v.designimg;
                } else {
                    designimg = DOMAIN_FILE + 'dist/imgs/img.png';
                }
                sHtml += '<li>'+
                    '<a href="front/Project/projectInfo.do?id='+ v.project.id +'">'+
                        //'<img class="am-thumbnail" src="' + designimg + '" />';
                        '<img class="am-thumbnail lazy" data-original="' + designimg + '" />';
                    '</a></li>';
            });
        } else {
            sHtml += '<div class="align-center no-projects">暂无相关作品</div>';
        }

        sHtml += '</ul>' +
            '</div>' +
        '</div>';
    });

    return sHtml;
}


