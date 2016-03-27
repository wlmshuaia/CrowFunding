/**
 * front/order/index.jsp 页面相关js操作
 * Created by wengliemiao on 16/3/1.
 */
$(function() {

});

/**
 * 增加数量
 * @param obj
 */
function fAddNum(obj) {
    var oNum = $(obj).siblings(".num-show");
    var iNum = parseInt(oNum.text());

    $(oNum).text(iNum + 1);
    $("input[name=num]").val(iNum + 1);

    // 计算小计和总价
    fCalPrice(obj, iNum + 1);
}

/**
 * 减少数量
 * @param obj
 */
function fReduceNum(obj) {
    var oNum = $(obj).siblings(".num-show");
    var iNum = parseInt(oNum.text());

    if (iNum > 1) {
        $(oNum).text(iNum - 1);
        $("input[name=num]").val(iNum - 1);

        fCalPrice(obj, iNum - 1);
    }
}

/**
 * 计算所有价格
 * @param obj
 */
function fCalPrice(obj, iNum) {
    var oPrice = $(obj).parents("form").find("input[name=price]");
    // 1.计算"小计"价格
    var price = parseFloat($(oPrice).val());
    var dSubTotal = price * iNum;
    $(".subtotalPrice").text(dSubTotal);

    // 2.计算总价格
    var dTotal = dSubTotal;
    $("#totalPrice").text(dTotal);
}



//****************************************************************************//
//*********************    微信支付相关 js    **********************************//
//****************************************************************************//




//****************************************************************************//
//*********************    编辑地址相关 js    **********************************//
//****************************************************************************//
//****************************************************************************//












