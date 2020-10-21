$(function() {
	reWindowSize();
	initDataGrid();
	initDataGridYuzhi();
})
/**
 * 表头信息
 */
var head = [ [ {
	"field" : "ck",
	"checkbox" : true
},  {
	"field" : "wlType",
	"title" : "样品类型",
	"width" : "80",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "wlCode",
	"title" : " 样品编码",
	"width" : "80",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "wlName",
	"title" : "样品名称",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "recordUserName",
	"title" : "操作人",
	"width" : 100,
	"align" : "center",
	"sortable" : false
}, {
	"field" : "recordTime",
	"title" : "操作时间",
	"width" : "160",
	"align" : "center",
	"sortable" : false,
	"formatter" : formateTime
} ] ];
/**
 * 物料名称列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/zjsys_basics/dataWlInfoAction_list.action",
		collapsible : false,
		pagination : true,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : { // 参数传递
			wlName : $("#wlName_search").textbox('getValue'),
			wlCode : $("#wlCode_search").textbox('getValue')
		},	onClickRow:function(index, row){
			func_searchYuzhi()
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
		wlName : $("#wlName_search").textbox('getValue'),
		wlCode : $("#wlCode_search").textbox('getValue')
	});
}
/**
 * 增加物料名称信息
 */
function func_add() {
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '添加 &nbsp;<font color="red">物料名称</font> ',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/basics/DataWlInfo/manager.html',
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
					url : getContextPath() + "/zjsys_basics/dataWlInfoAction_add.action",
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
 * 编辑物料名称信息
 */
function func_edit() {
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要更新的记录。', 'info');
		return;
	}
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '编辑 &nbsp;<font color="red">物料名称</font> ',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/basics/DataWlInfo/manager.html',
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
					url : getContextPath() + "/zjsys_basics/dataWlInfoAction_edit.action",
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
 * 删除物料名称信息
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
				url : getContextPath() + "/zjsys_basics/dataWlInfoAction_del.action",
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
/**复制**/
function func_copy(){
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要复制的记录。', 'info');
		return;
	}
	$.messager.confirm('复制', '确定复制数据么?', function(r) {
		if (r) {
			$.messager.progress({
				title : '请等待',
				msg : '复制数据中...'
			});
			$.ajax({
				url : getContextPath() + "/zjsys_basics/dataWlInfoAction_copy.action",
				type : "post",
				dataType : "json",
				data : "recordId=" + row.id,
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
