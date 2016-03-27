/**
 * 首页管理相关js操作
 * Created by wengliemiao on 15/12/20.
 */
$(function() {
    $("#ifHandle").on('show.bs.modal', Handler.fHandler);
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

            // 点击确定删除
            modal.find(".btnConfirm").bind("click", {url: 'admin/Page/deletePage.do', id: aVal[0]}, CommonHandler.fToDeleteNormal);
        } else if("edit" == aVal[1]) { // 修改
            //Handler.fHandlerEdit(modal, "admin/Project/updateProject.do", aVal[0]);
            Handler.fHandlerEdit(modal, "admin/Page/updatePage.do", aVal[0]);
        } else if("check" == aVal[1]) { // 查看项目详情
            //Handler.fHandlerCheck(modal, "admin/Project/getProjectInfoById.do", aVal[0]);
        }
    },
    /**
     * 编辑首页轮播图
     * @param modal
     * @param url
     * @param data
     */
    fHandlerEdit: function(modal, url, data) {
        var oForm = modal.find(".modal-body form");
        oForm.show();
        modal.find(".modal-body p").hide();

        // 获取Page对象
        var obj = CommonHandler.fGetObjInfoJson(data, "admin/Page/getObjById.do");

        // 给编辑框赋值
        oForm.find("input[name=id]").val(data);
        oForm.find("input[name=name]").val(obj.name);

        oForm.find("#poster-con").find("#poster").remove();
        oForm.find("#poster-con").html('<input id="poster" name="poster" type="file" class="file-loading">');
        $("#poster").fileinput({
            overwriteInitial: true,
            showClose: false,
            showCaption: false,
            browseLabel: '',
            removeLabel: '',
            browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
            removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
            removeTitle: 'Cancel or reset changes',
            elErrorContainer: '#kv-avatar-errors',
            msgErrorClass: 'alert alert-block alert-danger',
            defaultPreviewContent: '<img src="' + obj.posterurl +'" alt="Your Avatar" style="width:160px">',
            layoutTemplates: {main2: '{preview} {remove} {browse}'},
            allowedFileExtensions: ["jpg", "png", "gif"]
        });

        oForm.find("input[name=linkurl]").val(obj.linkurl);
        oForm.find("input[name=status][value="+ obj.status +"]").prop("checked", true);

        // 绑定点击事件
        modal.find(".btnConfirm").bind("click", {url: url}, CommonHandler.fToSubmitFileNormal);
    },
};

