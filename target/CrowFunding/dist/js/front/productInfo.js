/**
 * 商品详情相关js操作
 * Created by wengliemiao on 15/12/22.
 */

$(function() {
    $(".product-header li").bind("click", fShowTab);

    //$("img.lazy").lazyload();
});

/**
 * 显示选项卡
 */
function fShowTab() {
    var index = $(this).index();

    // 显示选项
    $(".product-header li").removeClass("active");
    $(this).addClass("active");

    // 显示内容
    $(".p-obj").hide();
    $(".p-obj").eq(index).fadeIn();

    // 获取内容区域
    var sObjHtml = $(".p-obj").eq(index).html();
    if(sObjHtml == "" || sObjHtml == undefined) {
        // 商品id
        var proid = $("input[name=productid]").val();
        var sType = $(this).attr("id");

        var sSizeHtml = '';
        if("h-care" == sType) { // 洗护
            //sSizeHtml = CommonHandler.fGetObjInfoJson(proid, "front/Product/getObjCare.do").msg;
            sSizeHtml = CommonHandler.fGetObjInfoNormal(proid, "front/Product/getObjCare.do");
        } else if("h-sizetables" == sType) { // 尺码
            var pMap = CommonHandler.fGetObjInfoJson(proid, "front/Product/getObjSizetables.do");
            $.each(pMap, function(k, v){
                //sSizeHtml += '<img src="'+ v.sizetable +'">';
                sSizeHtml += '<img data-original="'+ DOMAIN_FILE + v.sizetable +'" class="lazy">';
            });
        }
        $(".p-obj").eq(index).html(sSizeHtml);
        $("img.lazy").lazyload();
    }
}


