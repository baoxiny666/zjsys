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
	"field" : "notice_title",
	"title" : "标题",
	"width" : 300,
	"align" : "center",
	"sortable" : false
}, 
{
	"field" : "notice_status",
	"title" : "发布状态",
	"width" : 100,
	"align" : "center",
	"sortable" : false
},
{
	"field" : "recordUser",
	"title" : "操作人",
	"width" : 100,
	"align" : "center",
	"sortable" : false
}, {
	"field" : "recordTime",
	"title" : "操作时间",
	"width" : 130,
	"align" : "center",
	"sortable" : false,
	"formatter" : formateTime
} ] ];
/**
 * 公告管理列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/tried_system/systemNoticeAction_list.action",
		collapsible : false,
		pagination : true,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		singleSelect : true,
		queryParams : { // 参数传递
			notice_title : $("#notice_title").textbox('getValue')
		},
		columns : head

	});
}


function func_search(){
	$('#datagrid').datagrid('load', {
		notice_title : $("#notice_title").textbox('getValue')
		});
}

/**
 * 增加公告管理信息
 */

function func_add() {
	
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '添加 &nbsp;<font color="red">公告管理</font> ',
		width : 770,
		height : 460,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/SystemNotice/manager.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			func_initRichText('richDivId','notice_content')
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
					url : getContextPath() + "/tried_system/systemNoticeAction_add.action",
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
 * 编辑公告管理信息
 */
function func_edit() {
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要更新的记录。', 'info');
		return;
	}
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '编辑 &nbsp;<font color="red">公告管理</font> ',
		width : 770,
		height : 460,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/SystemNotice/manager.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},

		onLoad : function() {
			$('#modelForm').form('load', row);
			func_fileEdit_list(row.id);
			func_initRichText('richDivId','notice_content');
			var _notice_content=func_relationContext(row.id,"notice_content");
			$("#notice_content").val(_notice_content);
			_RichEditor.txt.html(_notice_content);
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
					url : getContextPath() + "/tried_system/systemNoticeAction_edit.action",
					
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
 * 删除公告管理信息
 */
function func_del() {
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要更新的记录。', 'info');
		return;
	}
	 
	$.messager.confirm('删除', '确定删除数据么?', function(r) {
		if (r) {
			$.messager.progress({
				title : '请等待',
				msg : '删除数据中...'
			});
			$.ajax({
				url : getContextPath() + "/tried_system/systemNoticeAction_del.action",
				type : "post",
				dataType : "json",
				data : "id=" + row.id,
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
