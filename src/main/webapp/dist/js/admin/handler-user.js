/**
 * 后台用户 js 操作
 * Created by wengliemiao on 16/1/18.
 */
$(function() {
    //$("#ifHandle").modal();
    $("#ifHandle").on('show.bs.modal', Handler.fHandler);
});

var Handler = {
    /**
     * 判断操作类型
     * @param event
     * @returns {boolean}
     */
    fHandler: function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        // recipient: id-handle => 1-delete, 2-edit, 3-add,
        var recipient = button.data('whatever'); // Extract info from data-* attributes

        // 模态框
        var modal = $(this);

        // 设置在 data-whatever 的数据
        if (recipient == undefined) { // 自动调用函数？
            return false;
        }

        // 不同操作
        var aVal = recipient.split("-");

        // 公共标题
        modal.find(".modal-title").text("提示");

        if ("delete" == aVal[1]) { // 删除
            //Handler.fHandlerBase(modal, "admin/Product/deleteProduct.do", aVal[0], CommonHandler.fToDeleteNormal, "确认删除?");
        } else if ("edit" == aVal[1]) { // 修改
            Handler.fHandlerEdit(modal, "admin/User/updateUser.do", aVal[0]);
        } else if ("check" == aVal[1]) { // 查看商品详情
            Handler.fHandlerCheck(modal, "admin/User/getUserInfo.do", aVal[0]);
        } else if ("off" == aVal[1]) { // 下架
            //Handler.fHandlerBase(modal, "admin/Product/offSale.do", aVal[0], CommonHandler.fToSubmitKeyNormal, "确认下架?");
        } else if ("on" == aVal[1]) { // 上架
            //Handler.fHandlerBase(modal, "admin/Product/onSale.do", aVal[0], CommonHandler.fToSubmitKeyNormal, "确认上架?");
        }
    },
    /**
     * 编辑用户
     * @param modal
     * @param url
     * @param data
     */
    fHandlerEdit: function(modal, url, data) {
        modal.find(".modal-body p").hide();
        modal.find(".modal-body .obj-info").hide();
        var oForm = modal.find(".modal-body form");
        oForm.show();

        var oUser = CommonHandler.fGetObjInfoJson(data, "admin/User/getUserInfo.do");
        oForm.find("input[name=id]").val(oUser.id);
        if(oUser.headimgurl != undefined) {
            if(oUser.headimgurl.indexOf("http") > -1) {
                oForm.find(".avatar img").attr("src", oUser.headimgurl);
                oForm.find("input[name=headimgurl]").val(oUser.headimgurl);
            } else {
                oForm.find(".avatar img").attr("src", DOMAIN_FILE + oUser.headimgurl);
                oForm.find("input[name=headimgurl]").val(DOMAIN_FILE + oUser.headimgurl);
            }
        }

        oForm.find("input[name=openid]").val(oUser.openid);
        oForm.find("#openid").parent("p").css("display", "block");
        oForm.find("#openid").text(oUser.openid);
        oForm.find("input[name=nickname]").val(oUser.nickname);
        oForm.find("input[name=sex][value="+oUser.sex+"]").prop("checked", true);
        oForm.find("input[name=phone]").val(oUser.phone);
        oForm.find("input[name=province]").val(oUser.province);
        oForm.find("input[name=city]").val(oUser.city);
        oForm.find("input[name=language]").val(oUser.language);
        oForm.find("input[name=intro]").val(oUser.intro);
        oForm.find("input[name=type][value="+oUser.type+"]").prop("checked", true);

        modal.find(".modal-footer .btnConfirm").bind("click", {url: url}, CommonHandler.fToSubmitFileNormal);
    },
    /**
     * 查看用户信息
     * @param modal
     * @param url
     * @param data
     */
    fHandlerCheck: function(modal, url, data) {
        modal.find(".modal-body p").hide();
        modal.find(".modal-body form").hide();
        modal.find(".modal-body .obj-info").show();

        modal.find(".modal-title").text("信息");

        var oUser = CommonHandler.fGetObjInfoJson(data, url);

        if(oUser.headimgurl != undefined) {
            if(oUser.headimgurl.indexOf("http") > -1) {
                modal.find(".obj-img").find("img").attr("src", oUser.headimgurl);
            } else {
                modal.find(".obj-img").find("img").attr("src", DOMAIN_FILE + oUser.headimgurl);
            }
        }

        modal.find("#p-openid").text(this.fCheckNull(oUser.openid));
        modal.find("#p-nickname").text(oUser.nickname);

        var sSex = '';
        if(oUser.sex == 1) {
            sSex = "男";
        } else if(oUser.sex == 2) {
            sSex = "女";
        } else {
            sSex = "未知";
        }
        modal.find("#p-sex").text(sSex);

        modal.find("#p-phone").text(this.fCheckNull(oUser.phone));
        modal.find("#p-address").text(this.fCheckNull(oUser.province) + " " + this.fCheckNull(oUser.city));
        modal.find("#p-language").text(this.fCheckNull(oUser.language));
        modal.find("#p-intro").text(this.fCheckNull(oUser.intro));

        var sType = '';
        if(oUser.type == 0) {
            sType = '普通用户';
        } else if(oUser.type == 1) {
            sType = '设计师';
        } else {
            sType = '未知';
        }
        modal.find("#p-type").text(sType);

    },
    /**
     * 判断字符串是否为空
     * @param str
     * @returns {*}
     */
    fCheckNull: function(str) {
        if(str == null || str == undefined) {
            return '暂无';
        }
        return str;
    },
    fToChangeAvatar: function(obj) {
        $(obj).siblings("input[type=file]").click();
    },
    fShowAvatar: function(obj) {
        var reader = new FileReader();
        if (obj.files && obj.files[0]) {
            reader.readAsDataURL(obj.files[0]);
            var oImg = $(obj).siblings("img");
            reader.onload = function(e){
                $(oImg).attr("src", e.target.result);
                $(oImg).attr("width", "100%");
                $(oImg).attr("height", "100%");
            };
            //$(obj).siblings(".file-img-con").find("div").hide();
        }
    },
};


