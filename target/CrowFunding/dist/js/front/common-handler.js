/**
 * 前端通用操作类
 * Created by wengliemiao on 15/12/12.
 */
// 文件域名
//var DOMAIN_FILE = '<%=DataUtils.DOMAIN_FILE%>/';
//var DOMAIN_FILE = 'http://file.wenglm.cn/';
var DOMAIN_FILE = 'http://file.zm-college.com/';

var CommonHandler = {
    var: preNum = 1,
    /**
     * 获取对象信息: 返回json格式
     */
    fGetObjInfoJson: function (id, url) {
        var sObjInfo = "";
        $.ajax({
            url: url,
            method: 'get',
            data: {id: id},
            async: false,
            dataType: 'json',
            success: function (data) {
                sObjInfo = data;
            },
            error: function (data) {
                console.log("getObjInfo error...");
            }
        });
        return sObjInfo;
    },
    /**
     * 获取对象信息: 返回文本格式
     * @returns {string}
     */
    fGetObjInfoNormal: function (id, url) {
        var sObjInfo = "";
        $.ajax({
            url: url,
            method: 'get',
            data: {id: id},
            async: false,
            success: function (data) {
                sObjInfo = data;
            },
            error: function (data) {
                console.log("getObjInfo error...");
            }
        });
        return sObjInfo;
    },
    /**
     * 获取信息, 参数为json对象
     * @param aData
     * @param url
     */
    fGetInfoByJson: function (aData, url) {
        var sObjInfo = "";
        $.ajax({
            url: url,
            //method: 'get',
            //data: aData,
            async: false,
            method: 'post',
            data: JSON.stringify(aData),
            contentType: 'application/json', // 这句不加出现415错误:Unsupported Media Type
            success: function (data) {
                sObjInfo = data;
            },
            error: function (data) {
                console.log("fGetInfoByJson error...");
            }
        });
        return sObjInfo;
    },
    /**
     * 遍历项目列表,返回项目列表详情 html
     * @param projects
     * @returns {string}
     */
    fGetProjectsHtml: function (projects) {
        var sHtml = "";
        if (projects != null && projects.length > 0) {
            $.each(projects, function (k, v) {
                // 设计效果图
                var designimg = '';
                if (v.designimg != null && v.designimg[0] != undefined) {
                    designimg = v.designimg[0].projectimg; // 下面加 DOMAIN_file
                } else {
                    designimg = 'dist/imgs/img.png';
                }

                // 项目标题
                var title = '';
                if (v.project != null && v.project != undefined) {
                    title = v.project.title;
                } else {
                    title = '暂无';
                }

                // 是否关注
                var sFocus = '';
                if ("1" == v.ifFocus) {
                    sFocus = '<div class="over-con-on right" onclick="CommonHandler.fCancelFocusProject(this, ' + v.project.id + ');"></div>';
                } else {
                    sFocus = '<div class="over-con right" onclick="CommonHandler.fFocusProject(this, ' + v.project.id + ');"></div>';
                }

                // 项目状态
                var status = '';
                if(v.project.status == 1) {
                    status = '剩余' + v.leftdays + '天';
                } else {
                    status = CommonHandler.fGetProjectStatus(v.project.status);
                }

                sHtml += '<li>' +
                    '<div class="p-con">' +
                    '<div class="p-img">' +
                    '<a href="front/Project/projectInfo.do?id=' + v.project.id + '">' +
                         //'<img src="' + designimg + '" alt="">' +
                         '<img data-original="' + DOMAIN_FILE + designimg + '" alt="" class="lazy">' +
                    '</a>' +
                    '</div>' +
                    '<div class="p-info">' +
                    '<span class="title">' +
                    '   <a href="front/Project/projectInfo.do?id=' + v.project.id + '">' + title + '</a>' +
                    '</span>' +

                    '<div class="progress">' +
                        '<div class="am-progress-bar am-progress-bar-warning" style="width: ' + v.percent + '%;"></div>' +
                    '</div>' +

                    '<ul class="am-avg-sm-2 am-thumbnails">' +
                    '   <li>已筹' + v.fundnum + '件</li>' +
                        '<li class="am-text-right">'+ status +'</li>' +
                    '</ul>' + sFocus +
                    '</div>' +
                    '</div>' +
                    '</li>';
            });
        } else {
            //sHtml = '<div class="align-center no-projects">暂无</div>';
        }
        return sHtml;
    },
    /**
     * 关注项目
     * @param obj
     * @param id
     */
    fFocusProject: function (obj, id) {
        CommonHandler.fToFocus(obj, id, "front/User/ifFocusProject.do");
    },

    /**
     * 取消关注项目
     * @param obj
     * @param id
     */
    fCancelFocusProject: function (obj, id) {
        CommonHandler.fToCancelFocus(obj, id, "front/User/ifFocusProject.do");
    },
    /**
     * 关注设计师
     * @param obj
     * @param id
     */
    fFocusUser: function (obj, id) {
        CommonHandler.fToFocus(obj, id, "front/User/ifFocusUser.do");
    },
    /**
     * 取消关注设计师
     * @param obj
     * @param id
     */
    fCancelFocusUser: function (obj, id) {
        CommonHandler.fToCancelFocus(obj, id, "front/User/ifFocusUser.do");
    },
    /**
     * 关注
     * @param obj
     * @param id
     * @param url
     */
    fToFocus: function (obj, id, url) {
        var content = '';
        var aData = {};
        aData['id'] = id;
        aData['type'] = "focus";
        if ("success" == CommonHandler.fGetInfoByJson(aData, url)) {
            content = "关注成功!";
        } else {
            content = "关注失败!";
        }
        CommonHandler.fShowTips(content);

        if (content.indexOf("成功") > 0) {
            $(obj).removeClass("over-con");
            $(obj).addClass("over-con-on");
            //console.log($(obj).attr("onclick"));
            if ($(obj).attr("onclick").indexOf("Project") > 0) {
                $(obj).attr("onclick", 'CommonHandler.fCancelFocusProject(this, ' + id + ');');
            } else {
                $(obj).attr("onclick", 'CommonHandler.fCancelFocusUser(this, ' + id + ');');
            }
        }
        return content;
    },
    /**
     * 取消关注
     * @param obj
     * @param id
     */
    fToCancelFocus: function (obj, id, url) {
        var content = '';
        var aData = {};
        aData['id'] = id;
        aData['type'] = 'cancel';
        if ("success" == CommonHandler.fGetInfoByJson(aData, url)) {
            content = "取消关注成功!";
        } else {
            content = "取消关注失败!";
        }
        CommonHandler.fShowTips(content);

        if (content.indexOf("成功") > 0) {
            $(obj).removeClass("over-con-on");
            $(obj).addClass("over-con");

            if ($(obj).attr("onclick").indexOf("Project") > 0) {
                $(obj).attr("onclick", 'CommonHandler.fFocusProject(this, ' + id + ');');
            } else {
                $(obj).attr("onclick", 'CommonHandler.fFocusUser(this, ' + id + ');');
            }
        }
        return content;
    },
    /**
     * 显示消息提示
     * @param content
     */
    fShowTips: function (content) {
        layer.open({
            content: content,
            shade: false,
            style: 'background-color:#000; color:#fff; border:none;',
            time: 2
        });
    },
    /**
     * 项目状态转换
     * @param i
     * @returns {string}
     */
    fGetProjectStatus: function(i) {
        var status = '';
        switch (i) {
            case 0:
                status = '审核中';
                break;
            case 1:
                status = '筹集中';
                break;
            case 2:
                status = '被否决';
                break;
            case 3:
                status = '已达成';
                break;
            case 4:
                status = '未达成';
                break;
            default:
                status = '筹集中';
        }
        return status;
    },
    /**
     * 获取表单序列化数据，并返回json对象
     * @param obj
     */
    fGetFormSerializable: function(obj) {
        var fields = $(obj).serializeArray();

        // 组装json对象
        var sForm = {};
        $.each(fields, function(k, v){
            if(sForm[v.name]) {
                if($.isArray(sForm[v.name])) {
                    sForm[v.name].push(v.value);
                } else {
                    sForm[v.name] = [sForm[v.name], v.value];
                }
            } else {
                sForm[v.name] = v.value;
            }

        });
        return sForm;
    },
};

/**
 * 加载更多功能封装
 * @type {{fLoad: MyLoader.fLoad, Loader: MyLoader.Loader}}
 */
var MyLoader = {
    fLoad: function(params, url, sScrollCon, fGetHtml) {
        params['start'] = 1;
        params['count'] = 1;
        if(params['num'] == undefined) { // 默认加载8条数据
            params['num'] = 8;
        }
        $.each(params, function (k, v) {
            params[k] = v;
        });

        $(sScrollCon).after('<div class="my-loader-more align-center ">'+
                '<i class="am-icon-spinner am-icon-spin"></i>'+
                '<span>正在玩命的加载</span>'+
            '</div>');

        var app = new MyLoader.Loader($(sScrollCon), {
            url: url,
            params: params,
            scrollName: sScrollCon,  // 容器名称
            fGetHtml: fGetHtml
        });
        app.init();
    },
    Loader: function (element, options) {
        // 设置变量
        this.prev = this.next = this.start = options.params.start;

        // 获取'加载更多'对象
        var oLoader = $(options.scrollName).siblings(".my-loader-more");

        // 监听滚动事件
        this.init = function() {
            var _this = this;
            var ifLoad = false;

            this.renderList(options.params.start);

            $(window).scroll(function(){

                // 页面高度
                var totalH = $(window).height();
                // 可视区高度
                var viewH = document.body.clientHeight; // right
                // 滚动条到顶部垂直高度
                var scrollTop = $(window).scrollTop();
                // '加载更多'高度
                var loaderH = $(oLoader).height(); // right

                if((totalH - (viewH + scrollTop)) <= loaderH) {
                    if(ifLoad == false && $(oLoader).html().indexOf("暂无") == -1 && $(oLoader).html().indexOf("全部") == -1) {
                        ifLoad = true;
                        _this.next += options.params.count;
                        _this.renderList(_this.next);
                        ifLoad = false;
                    }
                }
            });
        };

        // 获取数据
        this.renderList = function(start) {
            options.params['start'] = start;

            var tMap = CommonHandler.fGetInfoByJson(options.params, options.url);

            //console.log(tMap);

            // 加载完毕
            if(tMap == null || tMap.length == 0 || tMap.length < options.params['num']) {
                //$(oLoader).html('<span>暂无更多内容</span>');
                $(oLoader).html('<span>已加载全部</span>');
            }

            // 返回 html 结构
            var sHtml = "";
            if(options.fGetHtml != null) {
                sHtml = options.fGetHtml(tMap);
            } else {
                sHtml = CommonHandler.fGetProjectsHtml(tMap);
            }

            // 判断是否 "已加载全部"
            if(sHtml.indexOf("暂时没有") > -1) {
                $(oLoader).remove();
            }

            if($(oLoader).length > 1) {
                $(oLoader).eq(1).remove();
            }

            if($(options.scrollName).html() == '' || $(options.scrollName).html().indexOf("加载") > -1) {
                $(options.scrollName).html(sHtml);
            } else {
                $(options.scrollName).append(sHtml);
            }

            if(sHtml.indexOf("暂时没有") == -1 && sHtml.indexOf("加载") == -1) {
                var oImg = $("li").find(".p-con");
                var aImg = $(oImg).find("img");
                $(aImg).css("height", $(oImg).width() + "px");
            }

            // 图片懒加载
            $("img.lazy").lazyload();
        };
    }
};









