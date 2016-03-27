/**
 * 发布项目公共js操作
 * Created by wengliemiao on 15/12/12.
 */
$(function(){
    $(".file-img-con").css("height", $("#multipleChoose").width() + "px");
});

/**
 * 通过config接口注入权限验证配置
 */
function fWechatConfig(url) {
    $.ajax({
        url: 'Pay/getConfigParams.do',
        data: {url: url},
        success: function(data) {
            // 注入权限验证配置
            wx.config({
                //debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.appid, // 必填，公众号的唯一标识
                timestamp: data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.noncestr, // 必填，生成签名的随机串
                signature: data.sign,// 必填，签名，见附录1
                jsApiList: [ // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                    'checkJsApi',
                    'chooseImage',  // 调用微信 选择图片 接口
                    'uploadImage'   // 调用微信 上传图片 接口
                ]
            });
        },
        error: function(data) {
            alert("config error");
        }
    });
}

// 5.1 拍照、本地选图
var images = {
    localId: [],
    serverId: []
};

// 通过ready接口处理成功验证
wx.ready(function() {
    /**
     * 选择图片接口
     */
    $("#multipleChoose").find(".file-img-con").bind("click", function() {
        wx.chooseImage({
            success: function (res) {
                //images.localId = res.localIds;
                if(res.localIds.length > 0) { // localIds 合并
                    Array.prototype.push.apply(images.localId, res.localIds);
                }

                var oImgUl = $(".upload-file-ul-con").find(".upload-file-ul");
                var iIndex = $(oImgUl).find("li").length - 1;
                for (var i = 0; i < res.localIds.length; i ++) {
                    // 1.生成新的 li 对象
                    var sHtml = '<li>' +
                            '<div class="file-img-con img-show" onclick="fDeleteImgFile(this);">' +
                                '<img src="" alt="" class="img-preview">' +
                            '</div>' +
                        '</li>';
                    $(sHtml).insertBefore($(oImgUl).find("#multipleChoose"));

                    // 2.获取 img 对象
                    var oImg = document.getElementsByClassName("img-preview")[iIndex + i];

                    // 3.预览图片
                    var width = $("#multipleChoose").width() + "px";
                    oImg.style.width = width;
                    oImg.style.height = width;
                    oImg.src = res.localIds[i];
                }
            },
            fail: function(res) {
                alert(JSON.stringify(res));
            }
        });
    });

});

/**
 * 删除图片
 * @param obj
 */
function fDeleteImgFile(obj) {
    // 1.获取点击的下标
    var index = $(obj).parents("li").index();

    // 2.移除显示的图片
    $(obj).parents(".upload-file-ul").find("li").eq(index).remove();

    // 3.移除 localId
    images.localId.splice(index, 1);
}

/**
 * 下一步: 公共部分判断
 * @returns {boolean}
 */
function fBeforeNextStep () {
    if($("input[name=title]").val() == '') {
        CommonHandler.fShowTips("作品名称不能为空");
    } else if($("textarea[name=intro]").val() == '') {
        CommonHandler.fShowTips("作品简介不能为空");
    } else if($(".img-show").length == 0) {
        CommonHandler.fShowTips("请上传展示图片");
    } else if($("input[name=label]").is(":checked") == false) {
        CommonHandler.fShowTips("请选择标签");
    } else {
        return true;
    }
    return false;
}

/**
 * 提交表单公共部分
 * @param url
 * @param fSuccess
 */
function fSubmitForm(url, fSuccess) {
    // 显示上传缓冲动画
    $(".loading-con").css("display", "table");

    // 使用 FormData 对象, 异步提交含文件表单
    var oFormData = new FormData(document.getElementById("addProjectForm"));

    // 调用微信上传图片接口, 再把图片下载到服务器
    var i = 0;
    var syncUpload = function() {
        wx.uploadImage({
            localId: images.localId[i ++],
            success: function (res) {
                oFormData.append("serverId", res.serverId);
                if(i == images.localId.length) { // 传完则提交表单
                    $.ajax({
                        url: url,
                        method: 'post',
                        data: oFormData,
                        processData: false,  // 告诉jQuery不要去处理发送的数据
                        contentType: false,  // 告诉jQuery不要去设置Content-Type请求头
                        success: function(data) { // 上传数据成功
                            fSuccess(data);
                        },
                        error: function(data) {
                            console.log(data);
                            alert("error~~~");
                            alert(data);
                            alert(data.msg);
                        }
                    });
                } else {
                    syncUpload();
                }
            }
        });
    };
    syncUpload();
}

