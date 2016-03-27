/**
 * 项目相关操作
 * Created by wengliemiao on 15/12/14.
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
        if("check" != aVal[1]) { // 不是查看项目操作
            // 模态框底部操作栏
            var sHandler = '<button type="button" class="btn btn-primary btnConfirm" data-dismiss="modal">确认</button>'+
                '<button type="button" class="btn btn-default btnCancel" data-dismiss="modal">取消</button>';
            modal.find("#handler-group").html(sHandler);
        }

        if("delete" == aVal[1]) { // 删除
            Handler.fHandlerDelete(modal, "admin/Project/deleteProject.do", aVal[0], CommonHandler.fToDeleteNormal);
        } else if("edit" == aVal[1]) { // 修改
            Handler.fHandlerEdit(modal, "admin/Project/updateProject.do", aVal[0]);
        } else if("pass" == aVal[1] || "against" == aVal[1]) { // 项目操作: 通过 或 否决
            Handler.fHandlerProject(modal, "admin/Project/projectHandler.do", aVal[0], aVal[1]);
        } else if("check" == aVal[1]) { // 查看项目详情
            Handler.fHandlerCheck(modal, "admin/Project/getProjectInfoById.do", aVal[0]);
        }
    },
    /**
     * 删除项目操作
     * @param modal
     * @param url
     * @param data
     * @param func
     */
    fHandlerDelete: function(modal, url, data, func) {
        modal.find(".modal-body p").show();
        modal.find("form").hide();
        modal.find(".obj-info").hide();
        modal.find(".modal-footer .obj-tip").hide();

        // 绑定删除事件
        CommonHandler.fIfDelete(modal, url, data, func);
    },
    /**
     * 编辑项目操作
     * @param modal
     * @param url
     * @param data
     */
    fHandlerEdit: function(modal, url, data) {
        // 表单对象
        var oForm = modal.find("form");

        oForm.show();
        modal.find(".modal-body p").hide();
        modal.find(".obj-info").hide();
        modal.find(".modal-footer .obj-tip").hide();

        // 获取项目信息
        var projectMap = CommonHandler.fGetObjInfoJson(data, "admin/Project/getProjectInfoById.do");

        // 项目信息赋值
        oForm.find("input[name=title]").val(projectMap.project.title);
        oForm.find("textarea[name=intro]").val(projectMap.project.intro);

        $("#designimg-con").find("#designimg").remove();
        $("#designimg-con").html('<input id="designimg" name="designimg" type="file" class="file-loading">');

        $("#designimg").fileinput({
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
            defaultPreviewContent: '<img src="' + DOMAIN_FILE + projectMap.pdList[0].projectimg +'" alt="Your Avatar" style="width:160px">',
            layoutTemplates: {main2: '{preview} {remove} {browse}'},
            allowedFileExtensions: ["jpg", "png", "gif"]
        });
        //oForm.find("#designimg").attr("src", projectMap.serverPath + projectMap.pdList[0].projectimg);

        // 商品列表
        var aPro = CommonHandler.fGetInfo("admin/Product/selectAllJson.do");
        var sProSelect = '<option value="">选择商品</option>';
        $.each(aPro, function(k, v) {
            if(projectMap.product.id == v.id) {
                sProSelect += '<option value="'+ v.id +'" selected>'+ v.proname +'</option>';
            } else {
                sProSelect += '<option value="'+ v.id +'">'+ v.proname +'</option>';
            }
        });
        var oProSelect = oForm.find("select[name=product]");
        // 显示商品列表
        oProSelect.html(sProSelect);
        // 绑定事件
        oProSelect.bind("change", Handler.fShowColorPrice);

        // 商品颜色
        var sColorHtml = '<option value="">选择颜色</option>';
        if(projectMap.product != null && projectMap.product.id != undefined) {
            $.each(projectMap.pcList, function(k, v) {
                if(v.color == projectMap.project.color) {
                    sColorHtml += '<option value="'+ v.id +'" selected>'+ v.color +'</option>';
                } else {
                    sColorHtml += '<option value="'+ v.id +'">'+ v.color +'</option>';
                }
            });
        }
        oForm.find("select[name=color]").html(sColorHtml);

        // 商品价格
        oForm.find("#pro-price").html(projectMap.product.price + " 元");

        // 多标签
        var aLabel = CommonHandler.fGetInfo("admin/Label/selectAllJson.do") ;
        var sLabelHtml = '';
        $.each(aLabel, function(k, v) {
            if(CommonHandler.fIfHasObject(projectMap.labelList, v) == true) {
                sLabelHtml += '<label class="checkbox-inline"><input type="checkbox" name="label" value="'+ v.id +'" checked> '+ v.labelname +'</label> ';
            } else {
                sLabelHtml += '<label class="checkbox-inline"><input type="checkbox" name="label" value="'+ v.id +'"> '+ v.labelname +'</label> ';
            }
        });
        $("#labelList").html(sLabelHtml);

        // 标签信息

        //var sLabel = '' ;
        //$.each(projectMap.labelList, function(k, v) {
        //    sLabel += v.labelname +" ";
        //});
        //oBody.find("#p-label").html(sLabel);
        //
        //if(projectMap.user.type == 0) {
        //    $(".user-designer").addClass("un-display");
        //} else {
        //    $(".user-designer").removeClass("un-display");
        //}
        //
        //oBody.find("#p-user").text(projectMap.user.nickname);
        //oBody.find("#p-date").html(projectMap.project.releasedate+"至<br>"+projectMap.project.enddate);
        //oBody.find("#p-targetnum").text(projectMap.project.targetnum);
        //
        //var iStatus = projectMap.project.status;
        //
        //oBody.find("#p-status").text(Handler.fGetStatus(iStatus));
        //
        //oBody.find(".obj-img").html('<img src="' + projectMap.serverPath + projectMap.pdList[0].projectimg +'" />');


        // 绑定编辑操作
        modal.find(".btnConfirm").bind("click", {url: url}, CommonHandler.fToSubmitNormal);
    },
    /**
     * 显示颜色和价格
     * @param obj
     */
    fShowColorPrice: function() {
        var iProId = $(this).val();
        if(iProId != null && iProId != undefined && iProId != "") {
            var map = CommonHandler.fGetObjInfoJson(iProId, "admin/Product/getObjInfo.do");
            $("#pro-price").text(map.product.price+" 元");
            if(map.pcList != null && map.pcList.length != 0) {
                var sColorHtml = '<option value="">选择颜色</option>';
                $.each(map.pcList, function(k, v) {
                    sColorHtml += '<option value="'+ v.color +'">'+ v.color +'</option>';
                });
                $("select[name=color]").html(sColorHtml);
            } else {
                $("select[name=color]").html("<option value=''>选择颜色</option>");
            }
        } else {
            $("select[name=color]").html("<option value=''>选择颜色</option>");
            $("#pro-price").text(0+" 元");
        }
    },

    /**
     * 项目操作: 通过 或 否决
     * @param modal
     * @param url
     * @param data
     * @param type
     */
    fHandlerProject: function(modal, url, data, type) {
        if("pass" == type) {
            modal.find(".modal-body p").text("确认通过?");
        } else if("against" == type) {
            modal.find(".modal-body p").text("确认否决?");
        }

        modal.find(".modal-body p").show();
        modal.find("form").hide();
        modal.find(".obj-info").hide();
        modal.find(".modal-footer .obj-tip").hide();

        // 绑定 通过或否决 操作
        modal.find(".btnConfirm").bind("click", {url: url, data: data, type: type}, Handler.fToHandleProject);
    },
    /**
     * 提交项目操作: 通过 或 否决
     * @param e
     */
    fToHandleProject: function(e) {
        $.ajax({
            url: e.data.url,
            data: {projectid: e.data.data, type: e.data.type},
            success: function(data) {
                if("success" == data) {
                    CommonHandler.fHandleSuccess("", "操作成功!");
                } else {
                    CommonHandler.fHandleTip("", data);
                }
            },
            error: function(data) {
                console.log("handle project error...");
            }
        });
    },
    /**
     * 查看项目详情: 点击项目名称操作
     * @param modal
     * @param url
     * @param data
     */
    fHandlerCheck: function(modal, url, data) {
        modal.find(".obj-info").show();
        modal.find(".modal-footer .obj-tip").show();
        modal.find("form").hide();
        modal.find(".modal-body p").hide();

        // 获取项目信息
        Handler.fGetProjectInfo(modal, url, data);
    },
    /**
     * 获取并设置项目信息
     * @param modal
     * @param url
     * @param data
     * @param handler
     */
    fGetProjectInfo: function(modal, url, data) {
        modal.find(".modal-title").text("项目详情");

        var oBody = modal.find('.modal-body').eq(0);

        // 获取项目信息
        var projectMap = CommonHandler.fGetObjInfoJson(data, url);

        // 项目信息赋值
        oBody.find("#p-title").text(projectMap.project.title);
        oBody.find("#p-projectnum").text(projectMap.project.projectno);
        oBody.find("#p-intro").text(projectMap.project.intro);
        oBody.find("#p-product").text(projectMap.product.proname);
        oBody.find("#p-color").text(projectMap.project.color);
        oBody.find("#p-price").text(projectMap.product.price + " 元");

        var sLabel = '' ;
        $.each(projectMap.labelList, function(k, v) {
            sLabel += v.labelname +" ";
        });
        oBody.find("#p-label").html(sLabel);

        if(projectMap.user.type == 0) {
            $(".user-designer").addClass("un-display");
        } else {
            $(".user-designer").removeClass("un-display");
        }

        oBody.find("#p-user").text(projectMap.user.nickname);
        oBody.find("#p-date").html(projectMap.project.releasedate+"至<br>"+projectMap.project.enddate);
        oBody.find("#p-targetnum").text(projectMap.project.targetnum);

        var iStatus = projectMap.project.status;

        oBody.find("#p-status").text(Handler.fGetStatus(iStatus));

        oBody.find(".obj-img").html('<img src="' + DOMAIN_FILE + projectMap.pdList[0].projectimg +'" />');

        // 根据项目状态设置操作
        var sHandler = '';
        if(0 == iStatus) { // 审核状态有: 通过和否决操作
            sHandler += '<button type="button" class="btn btn-success btnPass" data-dismiss="modal">通过</button>'+
                '<button type="button" class="btn btn-warning btnUnpass" data-dismiss="modal">否决</button>';

            // 绑定 通过 和 否决 操作
            modal.find(".btnPass").bind("click", {url: url, data: data, type: "pass"}, Handler.fToHandleProject);
            modal.find(".btnUnpass").bind("click", {url: url, data: data, type: "unpass"}, Handler.fToHandleProject);
        }

        // 默认操作
        sHandler += '<button type="button" class="btn btn-primary btnEdit" data-dismiss="modal">修改</button>'+
            '<button type="button" class="btn btn-danger btnDelete" data-dismiss="modal">删除</button>'+
            '<button type="button" class="btn btn-default btnCancel" data-dismiss="modal">取消</button>';

        // 绑定 编辑 和 删除 操作
        //modal.find(".btnEdit").bind("click", );
        //modal.find(".btnDelete").bind("click", );

        $("#handler-group").html(sHandler);
    },
    /**
     * 获取项目状态: 0 1 2 3
     * @param status
     */
    fGetStatus: function(status) {
        var sStatus = "";
        switch(status) {
            case 0:
                sStatus = "审核中";
                break;
            case 1:
                sStatus = "进行中";
                break;
            case 2:
                sStatus = "被否决";
                break ;
            case 3:
                sStatus = "已达成";
                break;
            case 4:
                sStatus = "未达成";
                break;
            default:
                sStatus = "审核中";
                break;
        }
        return sStatus;
    }
}
