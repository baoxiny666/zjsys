$(function() {
	reWindowSize();
	initDataGrid();
})
/**
 * 表头信息
 */
var head =[ [ {
	"field" : "ck",
	"checkbox" : true
},{
	"field" : "roleCode",
	"title" : "角色代码",
	"width" : 150,
	"sortable" : true
}, 

{
	"field" : "roleName",
	"title" : "角色名称",
	"width" : 160,
	"sortable" : true
}, {
	"field" : "roleContent",
	"title" : "角色描述",
	"width" : 100,
	"sortable" : false
}, {
	"field" : "flag",
	"title" : "是否启用",
	"width" : 100,
	"sortable" : false
} , {
	"field" : "_opt",
	"title" : "操作",
	"width" : 80,
	"align":"center",
	"formatter":function(value,row,index){   //格式化函数添加一个操作列
        var btn = '<input type="button" value="功能关联"  onclick="func_Menu(\''+row['id']+'\',\''+row['roleName']+'\')">';
        return btn; 
       }
}]];
/**
 * 功能关联
 * @param index
 */
function func_Menu(roleId,roleName){
	
	$('<div></div>').dialog({
		id : 'menuWindow',
		title : '<font color=red>'+roleName+'</font> 权限设置',
		width : 300,
		height : 400,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/SystemRole/menu.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			var setting = {
				check : {
					enable : true
				},
				data : {
					simpleData : {
						enable : true
					}
				}
			};
			 var zNodes =new Array();
				$.ajax({
					url : getContextPath() + "/tried_system/systemRoleAction_roleMenuTree.action?recordId="+roleId,
					type : "post",
					dataType : "json",
					async : false,
					success : function(DATA, request, settings) {
						if(DATA.STATUS=='SUCCESS'){
							zNodes=DATA.RETURN_DATA;
							$.each(zNodes, function(index) { 
								if(zNodes[index].icon!=''){
								  zNodes[index].icon=getContextPath() +'/img/icon/'+zNodes[index].icon; 
								}
							});
						}
					},
					error : function(event, request, settings) {
						$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
					}
				});
				$.fn.zTree.init($("#menuTree"), setting, zNodes);
		},
		buttons : [
		   {
				text : '保存',
				iconCls : 'icon-ok',
				handler : function() {
					$.messager.progress({
						title : '请等待',
						msg : '关联数据中...'
					});
					var zTree = $.fn.zTree.getZTreeObj("menuTree");
					var nodes = zTree.getCheckedNodes(true);
					var menuId="";
					for ( var i = 0; i < nodes.length; i++) {
						menuId=menuId+nodes[i].id+",";
						}
					$.ajax({
						url : getContextPath() + "/tried_system/systemRoleAction_roleSetMenu.action",
						type : "post",
						dataType : "json",
						data : "recordId="+roleId+"&recordIdS=" + menuId,
						async : true,
						success : function(DATA, request, settings) {
							$.messager.progress('close');
							if (DATA.STATUS == 'SUCCESS') {
								$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: blue">关联成功</font>', 'info');
							} else {
								$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">关联失败</font>', 'warning');
							}
						},
						error : function(event, request, settings) {
							$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
						}
					});
					
					}
			},
			{
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$("#menuWindow").dialog('destroy');
			}
		} ]
	});
}
/**
 * 角色信息列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/tried_system/systemRoleAction_list.action",
		collapsible : false,
		pagination : true,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : { // 参数传递
			roleName : $("#roleName").textbox('getValue')
		},
		columns : head

	});
}

function func_search(){
	$('#datagrid').datagrid('load', {
		roleName : $("#roleName").textbox('getValue')
		});	
}
/**
 * 增加用户信息
 */
function func_add() {
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '添加角色信息',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/SystemRole/manager.html',
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
					url : getContextPath() + "/tried_system/systemRoleAction_add.action",
					onSubmit : function() {
						var isValid = $(this).form('enableValidation').form('validate');
						if (!isValid) {
							$.messager.progress('close');
						}
						return isValid;
					},
					success: function(data){
						var resultData = jQuery.parseJSON(data); 
						$.messager.progress('close');
						$("#modelWindow").dialog('destroy');
						if(resultData.STATUS=="SUCCESS") {
							func_msg_info(resultData.RETURN_DATA);
							initDataGrid();
						}else{
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
 * 编辑用户信息
 */
function func_edit() {
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要更新的记录。', 'info');
		return;
	}
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '编辑角色信息',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/SystemRole/manager.html',
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
					url : getContextPath() + "/tried_system/systemRoleAction_edit.action",
					onSubmit : function() {
						var isValid = $(this).form('enableValidation').form('validate');
						if (!isValid) {
							$.messager.progress('close');
						}
						return isValid;
					},
					success: function(data){
						var resultData = jQuery.parseJSON(data); 
						$.messager.progress('close');
						$("#modelWindow").dialog('destroy');
						if(resultData.STATUS=="SUCCESS") {
							func_msg_info(resultData.RETURN_DATA);
							initDataGrid();
						}else{
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
 * 删除用户信息
 */
function func_del() {
	var rowId = "";
	var rows = $('#datagrid').datagrid('getSelections');
	for ( var i = 0; i < rows.length; i++) {
		rowId = rowId + rows[i].id + ',';
	}
	if(rowId==""){
		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要删除的数据</font>','warning');
	return;}
	$.messager.confirm('删除', '确定删除数据么?', function(r) {
		if (r) {
			$.messager.progress({
				title : '请等待',
				msg : '删除数据中...'
			});
			$.ajax({
				url : getContextPath() + "/tried_system/systemRoleAction_del.action",
				type : "post",
				dataType : "json",
				data : "recordIdS=" + rowId,
				async : true,
				success : function(DATA, request, settings) {
					 $.messager.progress('close');
					 if(DATA.STATUS=='SUCCESS'){
						 func_msg_info(DATA.RETURN_DATA);
							 initDataGrid();
					 }else{
						 func_msg_info(DATA.RETURN_DATA); 
					 }
				},
				error : function(event, request, settings) {
					    func_msg_error("网络异常!");
				}
			});
		}
	});
}

/*
function exportExcel(){//获取Datagride的列  
    var rows = $('#datagrid').datagrid('getRows');  
    var columns = $("#datagrid").datagrid("options").columns[0];  
    var oXL = new ActiveXObject("Excel.Application"); //创建AX对象excel   
    var oWB = oXL.Workbooks.Add(); //获取workbook对象   
    var oSheet = oWB.ActiveSheet; //激活当前sheet  
    //设置工作薄名称  
    oSheet.name = "导出Excel报表";  
    //设置表头  
    for (var i = 0; i < columns.length; i++) {  
        oSheet.Cells(1, i+1).value = columns[i].title;  
    }  
    //设置内容部分  
    for (var i = 0; i < rows.length; i++) {  
        //动态获取每一行每一列的数据值  
        for (var j = 0; j < columns.length; j++) {                 
            oSheet.Cells(i + 2, j+1).value = rows[i][columns[j].field];  
        }     
    }                
    oXL.Visible = true; //设置excel可见属性  
}
*/
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
	$('#divConId').css("height",_height);
	$('#datagrid').css("height","97%");
}
