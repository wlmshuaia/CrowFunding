/**
 * 项目热议相关js操作
 * Created by wengliemiao on 16/1/2.
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
            modal.find(".modal-body p").show();
            modal.find(".modal-body form").hide();

            modal.find(".btnConfirm").bind("click", {url: 'admin/Comment/deleteComment.do', id: aVal[0]}, CommonHandler.fToDeleteNormal);
        } else if("edit" == aVal[1]) { // 修改
            //Handler.fHandlerEdit(modal, "admin/Project/updateProject.do", aVal[0]);
            Handler.fHandlerEdit(modal, "admin/Theme/updateTheme.do", aVal[0]);
        } else if("check" == aVal[1]) { // 查看项目详情
            //Handler.fHandlerCheck(modal, "admin/Project/getProjectInfoById.do", aVal[0]);
        } else if("pass" == aVal[1]) { // 通过评论
            CommonHandler.fIfHandle(modal, "admin/Comment/setCommonPass.do", aVal[0], CommonHandler.fToSubmitKeyNormal, "确认通过?");
        } else if("against" == aVal[1]) { // 否决评论
            CommonHandler.fIfHandle(modal, "admin/Comment/setCommonAgainst.do", aVal[0], CommonHandler.fToSubmitKeyNormal, "确认否决?");
        }
    },

};



