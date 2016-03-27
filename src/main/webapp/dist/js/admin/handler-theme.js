/**
 * 主题列表相关js操作
 * Created by wengliemiao on 15/12/19.
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

            modal.find(".btnConfirm").bind("click", {url: 'admin/Theme/deleteTheme.do', id: aVal[0]}, CommonHandler.fToDeleteNormal);
        } else if("edit" == aVal[1]) { // 修改
            //Handler.fHandlerEdit(modal, "admin/Project/updateProject.do", aVal[0]);
            Handler.fHandlerEdit(modal, "admin/Theme/updateTheme.do", aVal[0]);
        } else if("check" == aVal[1]) { // 查看项目详情
            //Handler.fHandlerCheck(modal, "admin/Project/getProjectInfoById.do", aVal[0]);
        }
    },
    /**
     * 编辑操作
     * @param modal 模态框
     * @param url   提交编辑表单地址
     * @param data  主题id
     */
    fHandlerEdit: function(modal, url, data) {
        var oForm = modal.find(".modal-body form");
        oForm.show();
        modal.find(".modal-body p").hide();

        var themeMap = CommonHandler.fGetObjInfoJson(data, "admin/Theme/getThemeInfoById.do");

        // 主题id
        oForm.find("input[name=id]").val(data);

        // 主题名称
        oForm.find("input[name=name]").val(themeMap.theme.name);

        // 海报设置
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
            defaultPreviewContent: '<img src="' + DOMAIN_FILE + themeMap.theme.posterurl +'" alt="Your Avatar" style="width:160px">',
            layoutTemplates: {main2: '{preview} {remove} {browse}'},
            allowedFileExtensions: ["jpg", "png", "gif"]
        });

        // 获取所有标签
        var aLabel = CommonHandler.fGetInfo("admin/Label/selectAllJson.do") ;
        // 标签设置
        var sLabelHtml = '';
        $.each(aLabel, function(k, v) {
            if(CommonHandler.fIfHasObject(themeMap.labelList, v) == true) {
                sLabelHtml += '<label><input type="checkbox" name="labels" value="'+ v.id +'" checked>' + v.labelname + '</label> ';
            } else {
                sLabelHtml += '<label><input type="checkbox" name="labels" value="'+ v.id +'">' + v.labelname + '</label> ';
            }
        });
        oForm.find("#theme-label").html(sLabelHtml);

        // 是否显示
        oForm.find("input[name=status][value="+ themeMap.theme.status + "]").prop("checked", true);

        // 提交表单
        modal.find(".btnConfirm").bind("click", {url: url}, CommonHandler.fToSubmitFileNormal);
    },
};



