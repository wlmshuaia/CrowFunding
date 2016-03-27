/**
 * Created by wengliemiao on 15/12/12.
 */
$(function() {
    // ifHandler 模态框事件绑定
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
        if("delete" == aVal[1]) { // 删除
            CommonHandler.fIfDelete(modal, "admin/Label/deleteLabel.do", aVal[0], CommonHandler.fToDeleteNormal);
        } else if("edit" == aVal[1]) { // 编辑
            modal.find(".modal-title").text("编辑标签");

            var oForm = modal.find('.modal-body form').eq(0);

            var obj = CommonHandler.fGetObjInfoJson(aVal[0], 'admin/Label/getObjById.do');

            oForm.find("input[name=id]").val(obj.id);
            oForm.find("input[name=type]").val(obj.type);
            oForm.find("input[name=labelname]").val(obj.labelname);
            oForm.find("input[name=status]").prop("checked", false);
            oForm.find("input[name=status][value="+obj.status+"]").prop("checked", true);

            oForm.show();
            modal.find('.modal-body p').hide();
            modal.find(".btnConfirm").bind("click", {url: 'admin/Label/updateLabel.do'}, CommonHandler.fToSubmitNormal);
        } else if("add" == aVal[1]) { // 添加
            modal.find(".modal-title").text("添加标签");

            var oForm = modal.find('.modal-body form').eq(0);

            oForm.find("input[name=type]").val(0); // 标签类型: 默认为0

            oForm.show();
            modal.find('.modal-body p').hide();
            modal.find(".btnConfirm").bind("click", {url: 'admin/Label/addLabel.do'}, CommonHandler.fToSubmitNormal);
        }
    },
}
