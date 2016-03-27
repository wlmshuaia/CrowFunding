/**
 * 发布项目js操作备份
 * Created by wengliemiao on 16/3/15.
 */
$(function(){
    $(".file-img-con").css("height", $("#multipleChoose").width() + "px");
});

/**
 * 模拟点击 file 文件上传框
 * @param obj
 */
function fToClickFile(obj) {
    $(obj).siblings("input[type=file]").click();
}

// 要上传的 File 数组
var aFiles = new Array();

/**
 * 选择图片后触发 onchange 事件, 实现上传前图片的预览: 多选
 * @param input
 */
function fShowdImg(obj) {
    if(obj.files.length > 0) {
        Array.prototype.push.apply(aFiles, obj.files);
    }

    var oImgUl = $(obj).parents(".upload-file-ul-con").find(".upload-file-ul");
    var iIndex = $(oImgUl).find("li").length - 1;

    for (var i = 0; i < obj.files.length; i ++) {
        // 生成新的 li 对象
        var sHtml = '<li>' +
            '<div class="file-img-con img-show" onclick="fDeleteImgFile(this);">' +
            '<img src="" alt="" class="img-preview">' +
            '</div>' +
            '</li>';
        $(sHtml).insertBefore($(oImgUl).find("#multipleChoose"));

        // 获取 img 对象
        var oImg = document.getElementsByClassName("img-preview")[iIndex + i];

        // 预览图片
        if(obj.files && obj.files[i]) {
            oImg.style.width = "100%";
            oImg.style.height = "100%";
            oImg.src = window.URL.createObjectURL(obj.files[i]);
        }

    }
}

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
    //aFiles.splice(index, 1);

}

/**
 * 显示颜色和价格
 * @param obj
 */
function fShowColorPrice(obj) {
    var iProId = $(obj).val();
    if(iProId != null && iProId != undefined && iProId != "") {
        var map = CommonHandler.fGetObjInfoJson(iProId, "front/Product/getObjColorInfo.do");
        $("#pro-price").text(map.product.price);
        if(map.pcList != null && map.pcList.length != 0) {
            var sColorHtml = '<option value="">选择颜色</option>';
            $.each(map.pcList, function(k, v) {
                sColorHtml += '<option value="'+ v.color +'">'+ v.color +'</option>';
            });
            $("select[name=color]").html(sColorHtml);
        } else {
            $("select[name=color]").html("<option value=''>选择颜色</option>");
        }
    } else {
        $("select[name=color]").html("<option value=''>选择颜色</option>");
        $("#pro-price").text(0);
    }
}

/**
 * dataURL 转化为 Blob 对象
 * @param dataurl
 * @returns {*}
 */
function fDataURLtoBlob(dataurl) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}

/**
 * 下一步：判断是否为空
 */
function fNextStep() {

    var aInput = $("input[name=projectimgs]");
    var iFlag = false;
    for (var i = 0; i < aInput.length; i ++) {
        if($(aInput[i]).val() != '') {
            iFlag = true ;
            break;
        }
    }

    if($("input[name=title]").val() == '') {
        CommonHandler.fShowTips("作品名称不能为空");
    } else if($("textarea[name=intro]").val() == '') {
        CommonHandler.fShowTips("作品简介不能为空");
    } else if($(".img-show").length == 0) {
        CommonHandler.fShowTips("请上传展示图片");
    } else if($("input[name=label]").is(":checked") == false) {
        CommonHandler.fShowTips("请选择标签");
    } else if($("select[name=proid]").val() == '') {
        CommonHandler.fShowTips("请选择T恤款式");
    } else if($("select[name=color]").val() == '') {
        CommonHandler.fShowTips("请选择T恤颜色");
    } else {

        $(".loading-con").css("display", "table");

        // 使用 FormData 对象, 异步提交含文件表单
        var oFormData = new FormData(document.getElementById("addProjectForm"));

        // 从 aFiles 中获取 File 对象 append 到 FormData
        //for (var i = 0, file; i < aFiles.length, file = aFiles[i]; i ++) {
        //    oFormData.append("projectimgs", file);
        //    console.log("文件名称: " + file.name);
        //}

        // 获取所有的 canvas 转 toDataURL, 再转 Blob 对象, 再 append 到 FormData
        //for (var i = 0; i < $(".img-show").length; i ++) {
        //    alert("i = " + i);
        //    var canvas = document.getElementsByClassName("canvas-img")[i];
        //    alert(" 222");
        //    //var oCanvas = canvas.getContent("2d");
        //    var sDataUrl = canvas.toDataURL('image/png');
        //    alert(sDataUrl);
        //    var oImgBlob = fDataURLtoBlob(sDataUrl);
        //    alert("333");
        //    oFormData.append("projectimgs", oImgBlob, "image.jpg");
        //    alert("444");
        //}

        // 调用微信上传图片接口, 再把图片下载到服务器
        var i = 0;
        var syncUpload = function() {
            wx.uploadImage({
                localId: images.localId[i ++],
                success: function (res) {
                    oFormData.append("serverId", res.serverId);
                    if(i == images.localId.length) { // 传完则提交表单
                        $.ajax({
                            url: 'front/Project/addProjectHandle.do',
                            method: 'post',
                            data: oFormData,
                            processData: false,  // 告诉jQuery不要去处理发送的数据
                            contentType: false,  // 告诉jQuery不要去设置Content-Type请求头
                            success: function(data) { // 上传数据成功

                                // 1.显示成功提示
                                $(".loading-con").find(".loader").text("跳转中...");

                                // 2.页面跳转
                                var sUrl = "front/project/addProject-second.jsp?";
                                sUrl += "hour=" + data.timeMap.hour + "&";
                                sUrl += "minute=" + data.timeMap.minute +"&";
                                sUrl += "releasedate=" + data.releasedate + "&";
                                sUrl += "enddate=" + data.enddate +"&";
                                sUrl += "projectid=" + data.projectid +"&";
                                sUrl += "leastnum=" + data.leastnum +"&";
                                sUrl += "token=" + data.token ;

                                window.location.href = sUrl;
                            },
                            error: function(data) {
                                console.log(data);
                                alert("error~~~");
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
}


// 微信相关 js 接口调用
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
     * 判断当前客户端版本是否支持分享参数自定义
     */
    //wx.checkJsApi({
    //    jsApiList: [
    //        'chooseImage',
    //        'uploadImage'
    //    ],
    //    success: function (res) {
    //        //alert(JSON.stringify(res));
    //    }
    //});

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
