/**
 * 添加商品页面相关js
 * Created by wengliemiao on 15/12/10.
 */

$(function() {
    // 上传图片插件: 主图
    $("#input-mainimg").fileinput({
        'showUpload': false, // 隐藏上传按钮
        language: "zh",
        overwriteInitial: false,
        allowedFileExtensions: ["jpg", "png", "gif"],
    });

    // 上传图片插件: 上传详图
    $("#input-multi-detail").fileinput({
        'showUpload': false, // 隐藏上传按钮
        language: "zh",
        allowedFileExtensions: ["jpg", "png", "gif"],
    });

    // 选择颜色多选框,绑定事件
    $("input[name=color]").bind("click", fShowSpecify) ;

    $("#input-multi-sizetable").fileinput({
        showUpload: false,
        language: 'zh',
        allowedFileExtensions: ["jpg", "png", "gif"],
    });

});

/**
 * 显示规格主图
 * @param e
 */
function fShowSpecify(e) {
    // 颜色id
    var iColorId = $(this).val();

    if($(this).is(":checked") == true) { // 选中
        // 1.隐藏提示框
        $(".specify-tip").hide();

        var sColorName = $(this).siblings(".color-name").text();

        // 2.插入上传图片组件: 按照id顺序插入
        var sSpecify = '' ;
        sSpecify += '<div class="specify-img-con" id="specify-img-con-'+ iColorId +'" label="'+ iColorId +'">'+
                '<div id="kv-avatar-errors" class="center-block" style="width:160px;display:none"></div>'+
                '<input class="avatar" name="specifyimgs" type="file" class="file-loading">'+
                '<input type="text" name="colorname" class="form-control" value="'+ sColorName +'">'+
            '</div>';

        // 图片上传组件数组
        var aImgCon = $(".specify-img-con");
        // 组件数量为0 或者 新的 iColorId 最大
        if(aImgCon.length == 0 || parseInt(aImgCon.last().attr("label")) < parseInt(iColorId)) {
            $(".specify").append(sSpecify);
        } else { // 根据颜色id排序
            $.each(aImgCon, function(k, v) {
                var iId = $(v).attr("label");
                if(parseInt(iId) > parseInt(iColorId)) {
                    $(v).before(sSpecify);
                    return false;
                }
            });
        }

        // 3.设置上传图片样式: 接入fileInput插件
        $(".avatar").fileinput({
            overwriteInitial: true,
            //maxFileSize: 1500,
            showClose: false,
            showCaption: false,
            browseLabel: '',
            removeLabel: '',
            browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
            removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
            removeTitle: 'Cancel or reset changes',
            elErrorContainer: '#kv-avatar-errors',
            msgErrorClass: 'alert alert-block alert-danger',
            defaultPreviewContent: '<img src="'+ DOMAIN_FILE +'dist/imgs/preview.jpg" alt="Your Avatar" style="width:160px">',
            layoutTemplates: {main2: '{preview} {remove} {browse}'},
            allowedFileExtensions: ["jpg", "png", "gif"]
        });

    } else { // 取消选中
        // 1.移除相应图片上传插件
        $("#specify-img-con-"+iColorId).remove();
        if($(".specify-img-con").length == 0) { // 2.如果图片上传插件数量为0, 则显示提示框: spefify-tip
            $(".specify-tip").show();
        }
    }
}

/**
 * 显示子分类
 * @param obj  对象
 * @param type 类型: 一二三级分类
 */
function fShowChildCate(obj, type) {
    console.log($(obj).val());
    var pid = $(obj).val() ;

    $.ajax({
        url: 'admin/Product/getCateByPid.do',
        method: 'get',
        dataType: 'json',
        data: {pid: pid},
        contentType: 'application/json',
        success: function(data) {
            var iType = parseInt(type) ;
            // select 数组
            var aSelect = $(obj).parents(".select").eq(0).find("select");
            var oSelect = aSelect.eq(iType);

            // 填充子分类内容
            var sHtml = '';
            if(1 == iType) { // 点击选择一级分类
                sHtml += '<option value="">二级分类</option>';
                // 把三级分类内容置为空
                var oThird = aSelect.eq(iType+1);
                oThird.html('<option value="">三级分类</option>');
            } else if(2 == iType) { // 点击选择二级分类
                sHtml += '<option value="">三级分类</option>';
            }
            if(data.length != 0) {
                $.each(data, function(k, v) {
                    sHtml += '<option value="'+ v.id +'">'+ v.catename + '</option>';
                });
            }
            oSelect.html(sHtml);
        },
        error: function (data) {
            console.log("add-pro-base.js fShowChildCate error...");
        }
    });
}
