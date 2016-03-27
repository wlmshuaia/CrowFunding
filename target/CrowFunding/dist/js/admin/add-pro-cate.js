/**
 * 设置分类页面相关js
 */

$(function() {
    // 点击删除模态框事件绑定
    $('#ifHandle').on('show.bs.modal', fIfDelete);
});

/**
 * 不同操作类型
 * @param event
 */
function fIfDelete(event) {
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
    if("deleteCate" == aVal[1]) {
        modal.find('.modal-title').text("提示");
        var sHtml = '确定删除该分类及其所有子分类？<input type="hidden" name="id" value="'+aVal[0]+'">' ;
        modal.find('.modal-body p').html(sHtml);

        modal.find('.modal-body p').show();
        modal.find('.modal-body form').hide();

        modal.find(".btnConfirm").bind("click", {url: 'admin/Product/deleteCate.do'}, fToDelete);
    } else if("editCate" == aVal[1]) {

    }
}
/**
 * 删除分类操作: 不刷新页面
 * @param e
 */
function fToDelete(e){
    var id = $(".modal-footer").siblings(".modal-body").find("input[name=id]").val();
    console.log("id: "+id);
    $.ajax({
        url: e.data.url,
        method: 'get',
        //dataType: 'json',
        data: {id: id},
        success: function(data) {

            // 1.查找选中的li对象
            // 2.删除选中对象及其子分类
            var aLGroup = $(".list-group-item") ;
            $.each(aLGroup, function(k, v) {
                var oInput = $(v).find("input[name=id]") ;
                var cId = oInput.val();
                console.log("cId: "+cId);
                if(id == cId) {
                    // 删除当前对象及其子分类对象
                    var aChild = oInput.parents(".one-obj").nextAll();
                    $(aChild).find(".list-group").html("");

                    // 删除当前选中li标签对象
                    oInput.parents(".list-group-item").remove();

                    // 跳出提示
                    CommonHandler.fHandleTip("", "删除成功！");
                }
            });
        },
        error: function(data) {
            console.log("deleteUser error...");
        }
    });
}

/**
 * 显示删除按钮
 * @param obj
 */
function fShowHandle(obj) {
    $(obj).find(".delete-icon").show();
}
function fHideHandle(obj) {
    $(obj).find(".delete-icon").hide();
}

/**
 * 显示子分类
 * @param obj   当前对象
 * @param type  分类类型: 1 2 3 - 对应三级分类
 */
function fShowChildCate(obj, type) {
    // 获取当前点击的分类id
    var pid = $(obj).find("input[name=id]").val();
    // 设置选中样式
    $(obj).addClass("active");
    $(obj).siblings(".list-group-item").removeClass("active");

    $.ajax({
       url: 'admin/Product/getCateByPid.do',
        method: 'get',
        dataType: 'json' ,
        data: {pid: pid},
        contentType: 'application/json',
        success: function(data) {
            console.log(data);
            if(data != null) {

                // 分类类型
                var iType = 0;
                if("1" == type) { // 一级分类
                    iType = parseInt(type) ;
                    // 移除三级分类下的按钮 onclick 事件
                    var oThirdBtn = $(".one-obj").eq(iType+1).find(".btn") ;
                    oThirdBtn.attr("class", "btn btn-my");
                    oThirdBtn.removeAttr("onclick");
                } else if("2" == type) { // 二级分类
                    iType = parseInt(type) ;
                } else {
                    return false ;
                }

                // one-obj 对象
                var oSecond = $(".one-obj").eq(iType) ;

                // 1.将分类内容置为空
                var oSecondGroup = oSecond.find(".list-group");
                oSecondGroup.html("") ;
                $(".one-obj").eq(iType + 1).find(".list-group").html("");

                // 2.分类下的按钮添加onclick事件
                var oSecondBtn = oSecond.find(".btn");
                $(oSecondBtn).attr("onclick", "fAddCate(this, "+pid+", "+ (iType + 1) +")");

                // 3.填充分类内容
                var oFirstGroup = $(".one-obj").eq(iType).find(".list-group") ;
                var sHtml = '';
                $.each(data, function(k, v) {
                    sHtml += '<li class="list-group-item" onclick="fShowChildCate(this, '+(iType + 1)+')" ';
                    sHtml += 'onmouseover="fShowHandle(this)" onmouseout="fHideHandle(this)">';
                    sHtml += v.catename;
                    sHtml += '<input type="hidden" name="id" value="'+ v.id +'" >';
                    sHtml += '<a href="javascript:void(0)" class="btn-my-delete-icon delete-icon" ' +
                        'data-toggle="modal" data-target="#ifHandle" data-whatever="'+ v.id +'-deleteCate">';
                    sHtml += '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>';
                    sHtml += '</a>';
                    sHtml += '</li>';
                });
                oFirstGroup.html(sHtml);

            }
        },
        error: function (data) {
            console.log("show child cate error..");
        },
    });

}

/**
 * 点击分类下 "添加" 按钮,显示分类名称输入框
 * @param obj 对象
 * @param pid 父级id
 * @param type 类型
 */
function fAddCate(obj, pid, type) {
    var sHtml = '';
    sHtml += '<li class="list-group-item-edit">';
        sHtml += '<input type="text" class="form-control" name="catename" placeholder="请输入属性名称" autofocus>';
    sHtml += '</li>';

    // 添加输入框
    var oCon = $(obj).siblings(".one-obj-content");
    oCon.find(".list-group").append(sHtml);
    $(obj).text("确认");
    $(obj).attr("class", "btn btn-my-edit")
    $(obj).attr("onclick", "fToAddCate(this, "+pid+", "+ type +")");
}

/**
 * 点击分类下 "确认" 按钮,进行添加分类操作 - 对应添加操作
 * @param obj
 * @param pid
 * @returns {boolean}
 */
function fToAddCate(obj, pid, type) {
    var oListGroup = $(obj).siblings(".one-obj-content").find(".list-group") ;
    var catename = oListGroup.find("input[name=catename]").val();

    if("undefined" == catename || "" == catename) {
        CommonHandler.fHandleTip("", "分类名称不应为空!");
        return false ;
    }
    var oForm = {};
    oForm['catename'] = catename;
    oForm['pid'] = pid ;

    $.ajax({
        url: 'admin/Product/addCate.do',
        method: 'post',
        contentType: 'application/json', // 这句不加出现415错误: Unsupported Media Type
        data: JSON.stringify(oForm),
        success: function(data) {
            oListGroup.find(".list-group-item-edit").remove();
            $(obj).text("添加");
            $(obj).attr("class", "btn btn-my");
            $(obj).attr("onclick", "fAddCate(this, "+pid+", "+type+")");

            if("undefined" != data) {
                var sHtml = '' ;
                sHtml += '<li class="list-group-item" onclick="fShowChildCate(this, '+ type +')"' +
                    'onmouseover="fShowHandle(this)" onmouseout="fHideHandle(this)">';
                    sHtml += catename;
                    sHtml += '<input type="hidden" name="id" value="'+ data +'">' +
                    '<a href="javascript:void(0)" class="btn-my-delete-icon delete-icon"' +
                        'data-toggle="modal" data-target="#ifHandle" data-whatever="'+ data +'-deleteCate">' +
                            '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>' +
                    '</a>';
                sHtml += '</li>';
                oListGroup.append(sHtml);

                CommonHandler.fHandleTip("", "添加分类成功!");
            } else {
                CommonHandler.fHandleSuccess("", "添加分类失败!");
            }
        },
        error: function(data) {
            console.log("add cate error...");
        }
    });
}

