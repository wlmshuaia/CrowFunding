/**
 * 设置属性页面相关js
 */

$(function() {
    // 点击删除模态框事件绑定
    $('#ifHandle').on('show.bs.modal', Handler.fIfDelete);

    $("#add-color").bind("click", fToAddColor) ;
    $("#add-size").bind("click", fToAddSize) ;

});

/**
 * 添加颜色表单判断
 */
function fToAddColor() {
    if("" != $("input[name=colorname]").val()) {
        document.addColorForm.submit();
    } else {
        CommonHandler.fHandleTip("", "颜色名称不应为空!");
    }
}

/**
 * 添加尺码表单判断
 */
function fToAddSize() {
    if("" != $("input[name=sizename]").val()) {
        document.addSizeForm.submit();
    } else {
        CommonHandler.fHandleTip("", "尺码名称不应为空!");
    }
}

var Handler = {
    /**
     * 是否删除
     * @param event
     */
    fIfDelete: function(event) {
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
        if("deleteColor" == aVal[1]) {
            CommonHandler.fIfDelete(modal, "admin/Product/deleteAttr.do?type=color", aVal[0], CommonHandler.fToDeleteJson);
        } else if("editColor" == aVal[1]) {
            Handler.fIfEditBase(modal, aVal[0], "color") ;
        } else if("deleteSize" == aVal[1]) { // 删除尺码
            CommonHandler.fIfDelete(modal, "admin/Product/deleteAttr.do?type=size", aVal[0], CommonHandler.fToDeleteJson);
        } else if("editSize" == aVal[1]) { // 编辑尺码
            Handler.fIfEditBase(modal, aVal[0], "size") ;
        }
    },
    /**
     * 属性编辑基类
     * @param modal
     * @param data
     * @param type
     */
    fIfEditBase: function(modal, data, type) {
        modal.find(".modal-title").text("编辑");

        var oForm = modal.find('.modal-body form').eq(0);

        var obj = CommonHandler.fGetObjInfoJson(data, 'admin/Product/getAttrById.do?type='+type);

        oForm.find("input[name=id]").val(obj.id);

        if("color" == type) {
            oForm.find("input[name=name]").val(obj.colorname);

            // 移除上一次编辑的色卡选择框
            if($("#colorcard") != null) {
                $("#colorcard").remove();
            }

            // 添加颜色选择框
            var sColor = '<div class="form-group" id="colorcard">'+
                '<label for="recipient-username" class="col-sm-2 control-label">色卡:</label>'+
                '<div class="col-sm-10">'+
                '<input type="color" name="colorcard" class="form-control colorcard" value="'+ obj.colorcard +'">'+
                '</div>'+
                '</div>';

            oForm.append(sColor);
        } else if("size" == type) {
            // 移除上一次编辑的色卡选择框
            if($("#colorcard") != null) {
                $("#colorcard").remove();
            }

            oForm.find("input[name=name]").val(obj.sizename);
        }

        oForm.show();
        modal.find('.modal-body p').hide();
        modal.find(".btnConfirm").bind("click", {url: 'admin/Product/updateAttr.do', type: type}, Handler.fToSubmitForm);
    },
    /**
     * 提交表单
     * @param sFormName
     */
    fToSubmitForm: function(e) {
        var sForm = CommonHandler.fGetFormSerializable($("#editObjForm"));
        sForm['type'] = e.data.type ;
        $.ajax({
            url: e.data.url,
            method: 'post',
            data: JSON.stringify(sForm),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                CommonHandler.fHandleSuccess("", "操作成功！");
            },
            error: function(data) {
                console.log("saveObj error...");
            }
        });
    },
}
