/**
 * 发布服装类项目相关js操作
 * Created by wengliemiao on 16/3/15.
 */

/**
 * 显示颜色和价格
 * @param obj
 */
function fShowColorPrice(obj) {
    var sNoColorHtml = '<li class="no-color">请先选择款式</li>';

    var iProId = $(obj).val();
    if(iProId != null && iProId != undefined && iProId != "") {
        // 获取数据
        var map = CommonHandler.fGetObjInfoJson(iProId, "front/Product/getObjColorInfo.do");

        // 显示价格
        $("#pro-price").text(map.product.price);

        // 显示颜色选择
        if(map.pcList != null && map.pcList.length != 0) {
            var sColorHtml = '';
            $.each(map.pcList, function(k, v) {
                //sColorHtml += '<option value="'+ v.color +'">'+ v.color +'</option>';
                sColorHtml += '<li>' +
                        '<label>' +
                            '<div class="checkbox-pro-con">' +
                                '<input type="checkbox" value="'+ v.color +'" name="color" />' +
                            '<label></label>' +
                            '</div>' +
                            '<span class="checkbox-name">'+ v.color +'</span>' +
                        '</label>' +
                    '</li>';
            });
            $("#colorCon").html(sColorHtml);
        } else {
            $("#colorCon").html(sNoColorHtml);
        }
    } else {
        $("#colorCon").html(sNoColorHtml);
        $("#pro-price").text(0);
    }
}

/**
 * 下一步：提交表单并跳转
 */
function fNextStep() {
    if(fBeforeNextStep() == true) {
        if($("select[name=proid]").val() == '') {
            CommonHandler.fShowTips("请选择底衫款式");
        } else if($("#colorCon").text().indexOf("请先选择款式") > -1 || $("input[name=color]").is(":checked") == false) {
            CommonHandler.fShowTips("请选择底衫颜色");
        } else {
            fSubmitForm('front/Project/addProjectHandle.do', fSuccess);
        }
    }
}

/**
 * 表单成功
 * @param data
 */
function fSuccess(data) {
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
}
