/**
 * 基础属性设置
 */
// 文件域名
//var DOMAIN_FILE = '<%=DataUtils.DOMAIN_FILE%>/';
//var DOMAIN_FILE = 'http://file.wenglm.cn/';
var DOMAIN_FILE = 'http://file.zm-college.com/';

$(function() {
	// 编辑信息模态框属性设置
	$('#ifHandle').modal({
		backdrop: 'static', // 防止 点击空白 模态框消失
		keyboard: false,	// 防止 esc 键 模态框消失
		show: false
	});

	// 操作完成提示
	$('#handleSuccess').modal({
		backdrop: 'static',
		keyboard: false,
		show: false
	});

	$("#chooseAll").bind("click", {id: "#chooseAll", table: ".table"}, Choose.fChooseAll);
});

/**
 * 全选工具类
 */
var Choose = {
	/**
	 * 全选
	 */
	fChooseAll: function(e) {
		var oChAll = $(e.data.id);
		var aChBox = $(e.data.table).find("tbody input[type=checkbox]") ;
//		console.log($(oChAll).is(":checked"));
		
		if(true == $(oChAll).is(":checked")) { // 全选
			$(aChBox).prop("checked", true);
		} else { // 全不选
			$(aChBox).prop("checked", false);
		}
	},
};

/**
 * 后端通用操作类
 */
var CommonHandler = {
	/**
	 * 根据地址获取信息
	 * @param url
	 * @returns {string}
     */
	fGetInfo: function(url) {
		var sObjInfo = "";
		$.ajax({
			url: url,
			async: false,
			dataType: 'json',
			success: function(data) {
				sObjInfo = data;
			},
			error: function(data) {
				console.log("getInfo error...");
			}
		});
		return sObjInfo ;
	},
	/**
	 * 获取对象信息
	 */
	fGetObjInfoJson: function(id, url) {
		var sObjInfo = "";
		$.ajax({
			url: url,
			method: 'get',
			data: {id: id},
			async: false,
			dataType: 'json',
			success: function(data) {
				sObjInfo = data;
			},
			error: function(data) {
				console.log("getObjInfo error...");
			}
		});
		return sObjInfo ;
	},
	/**
	 * 是否操作
	 * @param modal
	 * @param url
	 * @param data
	 * @param fBtnConfirm
     * @param sHtml
     */
	fIfHandle: function(modal, url, data, fBtnConfirm, sHtml) {
		modal.find('.modal-title').text("提示");
		modal.find('.modal-body p').html(sHtml);

		modal.find('.modal-body p').show();
		modal.find('.modal-body form').hide();
		modal.find('.modal-body .obj-info').hide();

		modal.find(".btnConfirm").bind("click", {url: url, id: data}, fBtnConfirm);
	},
	/**
	 * 是否进行删除操作
	 * @param e
     */
	fIfDelete: function(modal, url, data, fBtnConfirm) {
		modal.find('.modal-title').text("提示");
		var sHtml = '确定删除？<input type="hidden" name="id" value="'+data+'">' ;
		modal.find('.modal-body p').html(sHtml);

		modal.find('.modal-body p').show();
		modal.find('.modal-body form').hide();

		modal.find(".btnConfirm").bind("click", {url: url}, fBtnConfirm);
	},
	/**
	 * 点击确定删除: 并刷新页面
	 * @param obj
	 */
	fToDeleteJson: function(e) {
		var id = $(".modal-footer").siblings(".modal-body").find("input[name=id]").val();
		$.ajax({
			url: e.data.url,
			method: 'get',
			dataType: 'json',
			data: {id: id},
			success: function(data) {
				CommonHandler.fHandleSuccess("", "删除成功！");
			},
			error: function(data) {
				console.log("deleteObj error...");
			}
		});
	},
	/**
	 * 删除对象: 操作后返回普通格式数据
	 */
	fToDeleteNormal: function(e) {
		var id = 0;
		if(e.data.id == null) {
			id = $(".modal-footer").siblings(".modal-body").find("input[name=id]").val();
		} else {
			id = e.data.id;
		}
		$.ajax({
			url: e.data.url,
			method: 'get',
			data: {id: id},
			success: function(data) {
				if("success" == data) {
					CommonHandler.fHandleSuccess("", "删除成功！");
				} else {
					CommonHandler.fHandleTip("", data);
				}
			},
			error: function(data) {
				console.log("deleteObj error..."+data);
			}
		});
	},
	/**
	 * 提交编辑
	 */
	fToSubmitJson: function(e) {
		var sForm = CommonHandler.fGetFormSerializable($("#editObjForm"));
		$.ajax({
			url: e.data.url,
			method: 'post',
			data: JSON.stringify(sForm),
			dataType: 'json',
			contentType: 'application/json',
			success: function(data) {
				CommonHandler.fHandleSuccess("", "修改成功！");
			},
			error: function(data) {
				console.log("saveObj error...");
			}
		});
	},
	/**
	 * 操作后返回普通格式数据
	 */
	fToSubmitNormal: function(e) {
		var sFormId = "#editObjForm";
		if(e.data.formId != null) {
			sFormId = "#" + e.data.formId;
		}
		var sForm = CommonHandler.fGetFormSerializable($(sFormId));
		$.ajax({
			url: e.data.url,
			method: 'post',
			data: JSON.stringify(sForm),
			contentType: 'application/json',
			success: function(data) {
				if("success" == data) {
					CommonHandler.fHandleSuccess("", "操作成功！");
				} else {
					CommonHandler.fHandleTip("", data);
				}
			},
			error: function(data) {
				console.log("saveObj error..."+data);
			}
		});
	},
	/**
	 * 异步提交包含文件表单
	 * @param e
     */
	fToSubmitFileNormal: function(e) {
		$.ajax({
			url: e.data.url,
			method: 'post',
			data: new FormData($("#editObjForm")[0]),
			processData: false,  // 告诉jQuery不要去处理发送的数据
			contentType: false,  // 告诉jQuery不要去设置Content-Type请求头
			success: function(data) {
				if("success" == data) {
					CommonHandler.fHandleSuccess("", "操作成功!");
				} else {
					CommonHandler.fHandleSuccess("", data);
				}
			},
			error: function(data) {
				console.log("fToSubmitFIleNormal error: "+data);
			}
		});

	},
	/**
	 * 带参数访问地址, 并输出反馈
	 * @param e
     */
	fToSubmitKeyNormal: function(e) {
		var data = {key: e.data.key};
		if(e.data.id != null) {
			data = {id: e.data.id};
		}
		$.ajax({
			url: e.data.url,
			method: 'post',
			data: data,
			success: function(data) {
				if("success" == data) {
					CommonHandler.fHandleSuccess("", "操作成功!");
				} else {
					CommonHandler.fHandleSuccess("", data);
				}
			},
			error: function(data) {
				console.log("fToSubmitFIleNormal error: "+data);
			}
		});
	},
	/**
	 * 获取表单序列化数据，并返回json对象
	 * @param obj
	 */
	fGetFormSerializable: function(obj) {
		var fields = $(obj).serializeArray();

		// 组装json对象
		var sForm = {};
		$.each(fields, function(k, v){
			if(sForm[v.name]) {
				if($.isArray(sForm[v.name])) {
					sForm[v.name].push(v.value);
				} else {
					sForm[v.name] = [sForm[v.name], v.value];
				}
			} else {
				sForm[v.name] = v.value;
			}

		});
		return sForm;
	},

	/**
	 * 操作成功: 并刷新页面
	 * @param title
	 * @param content
	 */
	fHandleSuccess: function(title, content) {
		var hsModal = $("#handleSuccess") ;
		// 打开模态框
		hsModal.modal("show");
		// 设置模态框内容
		if("" == title) {
			title = "提示";
		}
		hsModal.find(".modal-title").html(title);
		hsModal.find(".modal-body").html(content);
		hsModal.find(".btnConfirm").bind("click", CommonHandler.fReload);
	},
	/**
	 * 操作提示: 不刷新页面
	 * @param title
	 * @param content
     */
	fHandleTip: function(title, content) {
		var hsModal = $("#handleSuccess") ;
		// 打开模态框
		hsModal.modal("show");
		// 设置模态框内容
		if("" == title) {
			title = "提示";
		}
		hsModal.find(".modal-title").html(title);
		hsModal.find(".modal-body").html(content);
	},
	/**
	 * 页面重新加载
	 */
	fReload: function() {
		window.location.reload();
	},
	/**
	 * 判断对象列表中是否包含该对象
	 * @param list
	 * @param label
	 * @returns {boolean}
	 */
	fIfHasObject: function(list, obj) {
		var flag = false;
		$.each(list, function(k, v) {
			if(v.id == obj.id) {
				flag = true;
			}
		});
		return flag;
	},

	/**
	 * 批量操作类型
	 */
	fBatchHandler: function(type) {
		var handler = $("#batchHandler").val();
		if("deleteChoose" == handler) { // 批量删除
			CommonHandler.fDeleteChoose(type);
		}
	},

	/**
	 * 删除选中
	 */
	fDeleteChoose: function(type) {
		var aCheck = $("tbody input[type=checkbox]:checked");
		var sIds = "";

		if(aCheck.length == 0) {
			CommonHandler.fHandleTip("", "请选择要批量操作的数据!");
			return false;
		}

		$.each(aCheck, function (k, v) {
			sIds += $(v).val()+",";
		});

		var hModal = $("#handleSuccess");
		hModal.modal("show");

		hModal.find('.modal-title').text("提示");
		var sHtml = '确定批量删除?' ;
		hModal.find('.modal-body').html(sHtml);

		// 批量删除
		hModal.find(".btnConfirm").bind("click", {url: "admin/"+ type +"/batchDelete.do", key: sIds}, CommonHandler.fToSubmitKeyNormal);

	},

};
