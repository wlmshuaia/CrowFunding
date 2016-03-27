/**
 * 发布公益类项目相关js操作
 * Created by wengliemiao on 16/3/15.
 */
$(function() {
    //$("input[name=fundtype]").click(fChooseFundType);
});

/**
 * 公益类项目类型: 筹人, 筹钱
 */
//function fChooseFundType() {
//    if($(this).val() == 1) {
//        //$("#share").css("display", "block");
//        $("#share").show();
//    } else {
//        $("#share").hide();
//    }
//}

/**
 * 下一步：提交表单并跳转
 */
function fNextStep() {
    if(fBeforeNextStep() == false) {
        return ;
    }

    fSubmitForm('front/Project/addProjectCommonwealHandle.do', fSuccess);
}

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
    sUrl += "token=" + data.token + "&" ;
    sUrl += "fundtype=" + data.fundtype;

    window.location.href = sUrl;
}
