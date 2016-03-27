/**
 * 商品管理相关js操作
 * Created by wengliemiao on 16/1/3.
 */

$(function() {
    //$("#ifHandle").modal("toggle");
    // 点击删除模态框时间绑定
    $('#ifHandle').on('show.bs.modal', Handler.fHandler);
});

var Handler = {
    /**
     * 判断操作类型
     * @param event
     * @returns {boolean}
     */
    fHandler: function(event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        // recipient: id-handle => 1-delete, 2-edit, 3-add,
        var recipient = button.data('whatever'); // Extract info from data-* attributes

        // 模态框
        var modal = $(this);

        // 设置在 data-whatever 的数据
        if(recipient == undefined) { // 自动调用函数？
            return false;
        }

        // 不同操作
        var aVal = recipient.split("-");

        // 公共标题
        modal.find(".modal-title").text("提示");

        if("delete" == aVal[1]) { // 删除
            Handler.fHandlerBase(modal, "admin/Product/deleteProduct.do", aVal[0], CommonHandler.fToDeleteNormal, "确认删除?");
        } else if("edit" == aVal[1]) { // 修改
            //Handler.fHandlerEdit(modal, "admin/Project/updateProject.do", aVal[0]);
            Handler.fHandlerEdit(modal, "admin/Theme/updateTheme.do", aVal[0]);
        } else if("check" == aVal[1]) { // 查看商品详情
            Handler.fHandlerCheck(modal, "admin/Product/getProductById.do", aVal[0]);
        } else if("off" == aVal[1]) { // 下架
            Handler.fHandlerBase(modal, "admin/Product/offSale.do", aVal[0], CommonHandler.fToSubmitKeyNormal, "确认下架?");
        } else if("on" == aVal[1]) { // 上架
            Handler.fHandlerBase(modal, "admin/Product/onSale.do", aVal[0], CommonHandler.fToSubmitKeyNormal, "确认上架?");
        }
    },
    /**
     * 操作基类
     * @param modal
     * @param url
     * @param data
     * @param fBtnConfirm
     * @param tip
     */
    fHandlerBase: function(modal, url, data, fBtnConfirm, tip) {
        modal.find(".modal-body p").text(tip);
        modal.find(".modal-body p").show();
        modal.find(".modal-body form").hide();
        modal.find(".modal-body .obj-info").hide() ;

        modal.find(".btnConfirm").bind("click", {url: url, id: data}, fBtnConfirm);
    },
    fHandlerEdit: function(modal, url, data) {

    },
    fHandlerCheck: function(modal, url, data) {
        var obj = modal.find(".modal-body .obj-info");
        obj.show();
        modal.find(".modal-body p").hide();
        modal.find(".modal-body form").hide();

        var oMap = CommonHandler.fGetObjInfoJson(data, url);
        obj.find(".obj-img img").attr("src", DOMAIN_FILE + oMap.product.mainimg);
        obj.find(".p-proname").text(oMap.product.proname);
        obj.find(".p-prono").text(oMap.product.prono);
        obj.find(".p-price").text(oMap.product.price+" 元");
        obj.find(".p-cate").text(oMap.cate);

        var sSizeHtml = "";
        $.each(oMap.psList, function(k, v) {
            sSizeHtml += v.size + " ";
        });
        obj.find(".p-size").text(sSizeHtml);

        var sColorHtml = "";
        $.each(oMap.pcList, function(k, v) {
            sColorHtml += v.color + " ";
        });
        obj.find(".p-color").text(sColorHtml);

        obj.find(".p-leastnum").text(oMap.product.leastnum+" 件");
        obj.find(".p-care").text(oMap.product.care);
        obj.find(".p-sizetable").html('<img src="'+ DOMAIN_FILE + oMap.pstList[0].sizetable+'">');
        obj.find(".p-sizetable img").attr("width", "200px");
        obj.find(".p-sizetable img").attr("height", "150px");
    },
};



