$(function() {
	reWindowSize();
	initDataGrid();
})
/**
 * 表头信息
 */
var head = [ [ {
	"field" : "ck",
	"checkbox" : true
}, {
	"field" : "templateId",
	"title" : "templateId",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "serialNumber",
	"title" : "序号",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "serialName",
	"title" : "标题",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "serialKey",
	"title" : "关键字",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "recordTime",
	"title" : "recordTime",
	"width" : "140",
	"align" : "center",
	"sortable" : false,
	"formatter" : "formateTime"
}, {
	"field" : "recordUserName",
	"title" : "操作人",
	"width" : 100,
	"align" : "center",
	"sortable" : false
} ] ];
/**
 * 模板序列列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/jkgl_system/templateSerialAction_list.action",
		collapsible : false,
		pagination : true,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : { // 参数传递
			id : $("#id").val()
		},
		columns : head

	});
}

/**
 * 检索
 * 
 * @param gridId
 */
function func_search() {
	$('#datagrid').datagrid('load', {
		id : $("#id").val()
	});
}
/**
 * 增加模板序列信息
 */
function func_add() {
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '添加 &nbsp;<font color="red">模板序列</font> ',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/TemplateSerial/manager.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		buttons : [ {
			text : '保存',
			iconCls : 'icon-ok',
			handler : function() {
				$.messager.progress({
					title : '请等待',
					msg : '保存数据中...'
				});
				$('#modelForm').form('submit', {
					url : getContextPath() + "/jkgl_system/templateSerialAction_add.action",
					onSubmit : function() {
						var isValid = $(this).form('enableValidation').form('validate');
						if (!isValid) {
							$.messager.progress('close');
						}
						return isValid;
					},
					success : function(data) {
						var resultData = jQuery.parseJSON(data);
						$.messager.progress('close');
						$("#modelWindow").dialog('destroy');
						if (resultData.STATUS == "SUCCESS") {
							func_msg_info(resultData.RETURN_DATA);
							func_search();
						} else {
							func_msg_error(resultData.RETURN_DATA);
						}
					}
				});
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$("#modelWindow").dialog('destroy');
			}
		} ],
	});
}

/**
 * 编辑模板序列信息
 */
function func_edit() {
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要更新的记录。', 'info');
		return;
	}
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '编辑 &nbsp;<font color="red">模板序列</font> ',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/TemplateSerial/manager.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},

		onLoad : function() {
			$('#modelForm').form('load', row);
		},
		buttons : [ {
			text : '保存',
			iconCls : 'icon-ok',
			handler : function() {
				$.messager.progress({
					title : '请等待',
					msg : '保存数据中...'
				});
				$('#modelForm').form('submit', {
					url : getContextPath() + "/jkgl_system/templateSerialAction_edit.action",
					onSubmit : function() {
						var isValid = $(this).form('enableValidation').form('validate');
						if (!isValid) {
							$.messager.progress('close');
						}
						return isValid;
					},
					success : function(data) {
						var resultData = jQuery.parseJSON(data);
						$.messager.progress('close');
						$("#modelWindow").dialog('destroy');
						if (resultData.STATUS == "SUCCESS") {
							func_msg_info(resultData.RETURN_DATA);
							func_search();
						} else {
							func_msg_error(resultData.RETURN_DATA);
						}
					}

				});
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$("#modelWindow").dialog('destroy');
			}
		} ]
	});
}
/**
 * 删除模板序列信息
 */
function func_del() {
	var rowId = "";
	var rows = $('#datagrid').datagrid('getSelections');
	for ( var i = 0; i < rows.length; i++) {
		rowId = rowId + rows[i].id + ',';
	}
	if (rowId == "") {
		$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;">请选择要删除的数据</font>', 'warning');
		return;
	}
	$.messager.confirm('删除', '确定删除数据么?', function(r) {
		if (r) {
			$.messager.progress({
				title : '请等待',
				msg : '删除数据中...'
			});
			$.ajax({
				url : getContextPath() + "/jkgl_system/templateSerialAction_del.action",
				type : "post",
				dataType : "json",
				data : "recordIdS=" + rowId,
				async : true,
				success : function(DATA, request, settings) {
					$.messager.progress('close');
					if (DATA.STATUS == 'SUCCESS') {
						func_msg_info(DATA.RETURN_DATA);
						func_search();
					} else {
						func_msg_error(DATA.RETURN_DATA);
					}
				},
				error : function(event, request, settings) {
					func_msg_error("网络异常!");
				}
			});
		}
	});
}
/**
 * 页面缩放监听事件
 */
$(window).resize(function() {
	reWindowSize();
}).resize();
/**
 * 页面缩放自适应大小
 */
function reWindowSize() {
	var _parentId = $(window.parent.document).find("#tabContent");
	var _height = $(_parentId).height() - 48;
	$('#divConId').css("height", _height);
	$('#datagrid').css("height", "97%");
}
