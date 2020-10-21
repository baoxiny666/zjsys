var childHead= [ [ {
	"field" : "ck",
	"checkbox" : true
}, {
	"field" : "serialNumber",
	"title" : "条目号",
	"width" : 100,
	"align" : "center",
	"sortable" : false
}, {
	"field" : "serialKey",
	"title" : "报告关键字",
	"width" : 100,
	"align" : "center",
	"sortable" : false
}, {
	"field" : "serialName",
	"title" : "标题",
	"width" : 260,
	"align" : "center",
	"sortable" : false
},{
	"field" : "serialContent",
	"title" : "内容",
	"width" : 260,
	"align" : "center",
	"sortable" : false
},
{
	"field" : "checkValue",
	"title" : "默认值",
	"width" : 100,
	"align" : "center",
	"sortable" : false
},

{
	"field" : "serialBeizhu",
	"title" : "备注",
	"width" : 260,
	"align" : "center",
	"sortable" : false
},  {
	"field" : "recordUserName",
	"title" : "操作人",
	"width" : 130,
	"align" : "center",
	"sortable" : false
},{
	"field" : "recordTime",
	"title" : "操作时间",
	"width" : 140,
	"align" : "center",
	"sortable" : true,
	"formatter" : formateTime
}
] ];
function initDataGridChild() {
	var row = $('#datagrid').datagrid('getSelected');
	var _templateId="";
	if(row){
		_templateId=row.id;
	}
	$('#datagridChild').datagrid({
		toolbar : '#tbChild',
		url : getContextPath() + "/tried_system/templateSerialAction_list.action",
		collapsible : false,
		nowrap: false, //表格自动换行
		rownumbers : true, // 行号
		pagination : true,//分页
		striped : true, // 各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		singleSelect : true,
		columns : childHead,
		queryParams : {  //参数传递
			templateId:_templateId,
		}
	});
}
 
 
function func_searchChild(){
	var row = $('#datagrid').datagrid('getSelected');
	$('#datagridChild').datagrid('reload',{
		templateId:row.id
	}); 
}
//添加模板序列
function func_addChild(){
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要添加序列的模板记录。', 'info');
		return;
	}
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '添加模板 ：<font color="red">'+row.templateName+'</font>&nbsp;模板序列 ',
		width : 500,
		height :400,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/TemplateManager/child.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			$('#templateId_edit').val(row.id);
			$('#serialNumber_edit').textbox({
					onChange:function(newValue,oldValue){
						$('#serialKey_edit').textbox('setValue','${'+newValue+'}');
			}});
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
					url : getContextPath() + "/tried_system/templateSerialAction_add.action",
					onSubmit : function() {
						var _templateId=$('#templateId_edit').val();
						var _serialNumber=$('#serialKey_edit').textbox('getValue');
						if(!checkKey(_templateId,_serialNumber)){
							alert("报告关键字重复！，请换其他关键字")
							$.messager.progress('close');
							return false;
						}
						
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
function func_editChild(){
	var row = $('#datagridChild').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要更新的记录。', 'info');
		return;
	}
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '编辑 &nbsp;<font color="red">模板序列</font> ',
		width :500,
		height : 400,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/TemplateManager/child.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},

		onLoad : function() {
			$('#modelForm').form('load', row);
			$('#serialNumber_edit').textbox({
				onChange:function(newValue,oldValue){
					$('#serialKey_edit').textbox('setValue','${'+newValue+'}');
		   }});
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
					url : getContextPath() + "/tried_system/templateSerialAction_edit.action",
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
function func_delChild(){
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
				url : getContextPath() + "/tried_system/templateSerialAction_del.action",
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

function checkKey(templateId,serialKey){
	var _exitKey=false;
	$.ajax({
		url : getContextPath() + "/tried_system/templateSerialAction_checkKey.action",
		type : "post",
		data:"templateId="+templateId+"&serialKey="+serialKey,
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
			_exitKey=DATA.RETURN_DATA;
		},
		error : function(event, request, settings) {
			$.messager.progress('close');
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	return _exitKey
}