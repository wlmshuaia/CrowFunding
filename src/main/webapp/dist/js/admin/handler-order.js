/**
 * 订单管理相关js操作
 * Created by wengliemiao on 15/12/23.
 */

$(function() {
    //$('#ifHandle').modal('toggle');

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

        // 公共操作栏
        //if("check" != aVal[1]) { // 不是查看项目操作
        //    // 模态框底部操作栏
        //    var sHandler = '<button type="button" class="btn btn-primary btnConfirm" data-dismiss="modal">确认</button>'+
        //        '<button type="button" class="btn btn-default btnCancel" data-dismiss="modal">取消</button>';
        //    modal.find("#handler-group").html(sHandler);
        //}

        modal.find(".btnConfirm").text("确认");

        if("delete" == aVal[1]) { // 删除
            Handler.fHandlerDelete(modal, "admin/Order/deleteOrder.do", aVal[0], CommonHandler.fToDeleteNormal);
        } else if("deliver" == aVal[1] || "check" == aVal[1]) { // 发货 或者 查看项目详情
            Handler.fShowBaseInfo(modal, "admin/Order/fillExpress.do", aVal[0]);
        } else if("edit" == aVal[1] || "unpass" == aVal[1]) { // 项目操作: 通过 或 否决
            Handler.fHandlerEdit(modal, "admin/Order/updateOrder.do", aVal[0], aVal[1]);
        }
    },
    /**
     * 显示订单基本信息
     * @param modal
     * @param url
     * @param data
     */
    fShowBaseInfo: function(modal, url, data) {
        var obj = modal.find(".modal-body .obj-info");
        obj.show();
        modal.find(".modal-body p").hide();
        modal.find("#editObjForm").hide();

        var pMap = CommonHandler.fGetObjInfoJson(data, "admin/Order/getOrderInfo.do");

        obj.find(".obj-img img").attr("src", DOMAIN_FILE + pMap.designimg);
        obj.find("#p-projectnum").text(pMap.project.projectno);
        obj.find("#p-tradeno").text(pMap.order.tradeno);
        obj.find("#p-username").text(pMap.order.username);
        obj.find("#p-phone").text(pMap.order.phone);
        obj.find("#p-address").text(pMap.order.province + pMap.order.city + pMap.order.counties + pMap.order.address);
        obj.find("#p-backnum").text(pMap.pb.num + " 件");
        obj.find("#p-size").text(pMap.pb.size);
        obj.find("#p-price").text(pMap.order.price);
        obj.find("#p-status").text(Handler.fGetStatus(pMap.order.status));

        if(pMap.order.status == "0") {
            var sExpressHtml = '<input type="text" class="form-control" name="express" required placeholder="快递公司">' +
                '<input type="hidden" name="orderid" value="'+pMap.order.id+'">';
            obj.find("#p-express").html(sExpressHtml);
            obj.find("#p-trackno").html('<input type="text" class="form-control" name="trackno" required placeholder="运单号"></form>');
            modal.find(".btnConfirm").text("发货");
            //modal.find(".btnConfirm").attr("type", "submit");
            modal.find(".modal-footer .btnConfirm").bind("click", {url: url, formId: "expressForm"}, CommonHandler.fToSubmitNormal);
        } else {
            obj.find("#p-express").text(pMap.order.express);
            obj.find("#p-trackno").text(pMap.order.trackno);
        }
    },
    /**
     * 删除操作
     * @param modal
     * @param url
     * @param data
     */
    fHandlerDelete: function(modal, url, data) {
        modal.find(".modal-body p").show();
        modal.find("#editObjForm").hide();
        modal.find(".modal-body .obj-info").hide();

        modal.find(".btnConfirm").bind("click", {url: url, id: data}, CommonHandler.fToDeleteNormal);
    },
    /**
     * 返回订单状态
     * @param status
     */
    fGetStatus: function(status) {
        var sStatus = "";
        switch (status) {
            case 0:
                sStatus = "待发货";
                break;
            case 1:
                sStatus = "待收货";
                break;
            case 2:
                sStatus = "已收货";
                break;
            case 3:
                sStatus = "退款中";
                break;
            case 4:
                sStatus = "退款成功";
                break;
            case 5:
                sStatus = "退款失败";
                break;
            case 6:
                sStatus = "待支付";
                break;
            default:
                sStatus = "未知状态";
                break;
        }
        return sStatus;
    },
};

