/**
 * 表头信息
 */
var headChile = [ [ {
	"field" : "ck",
	"checkbox" : true
}, {
	"field" : "deviceName",
	"title" : "样品编码",
	"width" : "100",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "keyName",
	"title" : "元素",
	"width" : "60",
	"align" : "center",
	"sortable" : false
},  

{
	"field" : "keyMin",
	"title" : "最小阈值",
	"width" : "80",
	"align" : "center",
	"sortable" : false
},{
	"field" : "keyMax",
	"title" : "最大阈值",
	"width" : "80",
	"align" : "center",
	"sortable" : false
}, 
{
	"field" : "fieldName",
	"title" : "属性列",
	"width" : "60",
	"align" : "center",
	"sortable" : false
}, 
{
	"field" : "_optionsss",
	"title" : "操作",
	"width" : "200",
	"align" : "center",
	"sortable" : false,
	"formatter" : function(value, row, index) {
		var btn = '<input type="button" value="置顶" style="border:0" onclick="func_toFirst(' + index + ')">';
		btn = btn + ' <input type="button" value="上移"  style="border:0" onclick="func_toPre(' + index + ')">';
		btn = btn + ' <input type="button" value="下移"  style="border:0" onclick="func_toNext(' + index + ')">';
		btn = btn + ' <input type="button" value="置底"  style="border:0" onclick="func_toBottom(' + index + ')">';
		return btn;
	}
}, 
{
	"field" : "recordUserName",
	"title" : "操作人",
	"width" : 60,
	"align" : "center",
	"sortable" : false
}, {
	"field" : "recordTime",
	"title" : "操作时间",
	"width" : "120",
	"align" : "center",
	"sortable" : false,
	"formatter" : formateTime
} ] ];
/**
 * 设备属性阈值列表初始化
 */
function initDataGridYuzhi() {
	$('#childGrid').datagrid({
		toolbar : '#tbExc',
		url : getContextPath() + "/zjsys_testDataSrc/dataKeyMaxMinAction_list.action",
		collapsible : false,
		pagination : true,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		columns : headChile
	});
}

/**
 * 检索
 * 
 * @param gridId
 */
function func_searchYuzhi() {
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要更新的记录。', 'info');
		return;
	}
	$('#childGrid').datagrid('load', {
		deviceName : row.wlCode
	});
}
/**
 * 增加设备属性阈值信息
 */
function func_addMaxMin() {
	
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要更新的记录。', 'info');
		return;
	}
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '添加 &nbsp;<font color="red">属性阈值</font> ',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/basics/DataWlInfo/maxMin.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			$('#deviceName_edit').textbox('setValue', row.wlCode);
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
					url : getContextPath() + "/zjsys_testDataSrc/dataKeyMaxMinAction_add.action",
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
							func_searchYuzhi();
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
 * 编辑设备属性阈值信息
 */
function func_editMaxMin() {
	var row = $('#childGrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要更新的记录。', 'info');
		return;
	}
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '编辑 &nbsp;<font color="red">设备属性阈值</font> ',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/basics/DataWlInfo/maxMin.html',
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
					url : getContextPath() + "/zjsys_testDataSrc/dataKeyMaxMinAction_edit.action",
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
							func_searchYuzhi();
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
 * 删除设备属性阈值信息
 */
function func_delMaxMin() {
	var rowId = "";
	var rows = $('#childGrid').datagrid('getSelections');
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
				url : getContextPath() + "/zjsys_testDataSrc/dataKeyMaxMinAction_del.action",
				type : "post",
				dataType : "json",
				data : "recordIdS=" + rowId,
				async : true,
				success : function(DATA, request, settings) {
					$.messager.progress('close');
					if (DATA.STATUS == 'SUCCESS') {
						func_msg_info(DATA.RETURN_DATA);
						func_searchYuzhi();
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
 * 行置顶
 */
function func_toFirst(index){
	var firstRow = $('#childGrid').datagrid('getData').rows[0];
    var currentRow = $('#childGrid').datagrid('getData').rows[index];
    $('#childGrid').datagrid('getData').rows[0] = currentRow;
    $('#childGrid').datagrid('getData').rows[index] = firstRow;
    $('#childGrid').datagrid('refreshRow', 0);
    $('#childGrid').datagrid('refreshRow', index);
    $('#childGrid').datagrid('unselectAll');
    $('#childGrid').datagrid('selectRow', 0);
    func_sort();
}
/**
 * 行上移
 */
function func_toPre(index){
    if(index>0){
	    var preRow = $('#childGrid').datagrid('getData').rows[index-1];
	    var currentRow = $('#childGrid').datagrid('getData').rows[index];
	    $('#childGrid').datagrid('getData').rows[index-1] = currentRow;
	    $('#childGrid').datagrid('getData').rows[index] = preRow;
	    $('#childGrid').datagrid('refreshRow', index-1);
	    $('#childGrid').datagrid('refreshRow', index);
	    $('#childGrid').datagrid('unselectAll');
	    $('#childGrid').datagrid('selectRow', index-1);
	    func_sort();
	   
    }
}
/**
 * 行下移
 */
function func_toNext(index){
	var rows = $('#childGrid').datagrid('getRows').length;
	if (index != rows - 1) {
		   var nextRow = $('#childGrid').datagrid('getData').rows[index+1];
		    var currentRow = $('#childGrid').datagrid('getData').rows[index];
		    $('#childGrid').datagrid('getData').rows[index+1] = currentRow;
		    $('#childGrid').datagrid('getData').rows[index] = nextRow;
		    $('#childGrid').datagrid('refreshRow', index+1);
		    $('#childGrid').datagrid('refreshRow', index);
		    $('#childGrid').datagrid('unselectAll');
		    $('#childGrid').datagrid('selectRow', index+1);
		    func_sort();
	}
}
/**
 * 行置底
 */
function func_toBottom(index){
			var rows = $('#childGrid').datagrid('getRows').length;
		    var bottomRow = $('#childGrid').datagrid('getData').rows[rows-1];
		    var currentRow = $('#childGrid').datagrid('getData').rows[index];
		    $('#childGrid').datagrid('getData').rows[rows - 1] = currentRow;
		    $('#childGrid').datagrid('getData').rows[index] = bottomRow;
		    $('#childGrid').datagrid('refreshRow', rows-1);
		    $('#childGrid').datagrid('refreshRow', index);
		    $('#childGrid').datagrid('unselectAll');
		    $('#childGrid').datagrid('selectRow', rows-1);
		    func_sort();
}
/**
 * 排序
 */
function func_sort(){
	var rows = $('#childGrid').datagrid('getRows');
	var recordIds="";
	for(var i=0;i<rows.length;i++){
		recordIds=recordIds+rows[i].id+",";
	}
	$.ajax( {
		url : getContextPath() + "/tried_system/systemMenuAction_sort.action",
		type : "post",
		dataType : "json",
		data : "recordIdS=" + recordIds,
		async : true,
		success : function(DATA, request, settings) {
			 if(DATA.STATUS=='SUCCESS'){
				 initDataGrid();
			 }
		},
		error : function(event, request, settings) {
			$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>','error');
		}
	});
}

/**
 * 排序
 */
function func_sort(){
	var rows = $('#childGrid').datagrid('getRows');
	var recordIds="";
	for(var i=0;i<rows.length;i++){
		recordIds=recordIds+rows[i].id+",";
	}
	$.ajax( {
		url : getContextPath() + "/zjsys_testDataSrc/dataKeyMaxMinAction_sort.action",
		type : "post",
		dataType : "json",
		data : "recordIdS=" + recordIds,
		async : true,
		success : function(DATA, request, settings) {
			 if(DATA.STATUS=='SUCCESS'){
				 initDataGrid();
			 }
		},
		error : function(event, request, settings) {
			$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>','error');
		}
	});
}