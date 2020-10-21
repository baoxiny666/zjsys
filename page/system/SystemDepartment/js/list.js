$(function() {
	initTree();
	reWindowSize();
	initDataGrid();
	
})
//父节点id
var pId=0;
//父节点名称
var pName="";
/**
 * 初始化部门信息结构树
 */
function initTree(){
	var setting = {
			edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false,
				drag:{
					 isMove:true ,
					 isMove:true
				}
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick : reSetContent,
				beforeDrop: beforeDrop
			}
		};
	
	var zNodes =[{ id:1, pId:0, name:"机构树 ", open:true}];
		$.ajax({
			url : getContextPath() + "/tried_system/systemDepartmentAction_initTree.action",
			type : "post",
			dataType : "json",
			async : false,
			success : function(DATA, request, settings) {
				if(DATA.STATUS=='SUCCESS'){
					zNodes=DATA.RETURN_DATA;
				}
			},
			error : function(event, request, settings) {
				$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
			}
		});
		$.fn.zTree.init($("#menuTree"), setting, zNodes);
}
//单击事件
function reSetContent(event, treeId, treeNode, clickFlag) {	
	pId=treeNode.id;
	pName=treeNode.name;
	$("#name").val("");
	initDataGrid() ;
}

/**
 * 节点拖动
 * @param treeId
 * @param treeNodes
 * @param targetNode
 * @param moveType
 * @returns
 */
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
	var dropNodeId="";var parentId="";
	if(treeNodes.length==0){
		alert("请选择拖动点");return false;
	}
	
	for(var i=0;i<treeNodes.length;i++){
		dropNodeId=dropNodeId+treeNodes[i].id+',';
	}
	 parentId=targetNode.id;
	if(dropNodeId==""||parentId==""){return false;}
	var formData="recordIdS="+dropNodeId+"&parentId="+parentId;
	$.ajax( {
		url : getContextPath() + "/tried_system/systemDepartmentAction_drop.action",
		type : "post",
		dataType : "text",
		data : formData,
		async : false,
		success : function(data, request, settings) {
			if(data.STATUS=="SUCCESS"){
				return true;
			}
			else{
				return false;
			}
		},
		error : function(event, request, settings) {
		    	return false;
		}
	});
	
}
 
/**
 * 表头信息
 */
var head = [[ {
	"field" : "ck",
	"checkbox" : true
}, {
	"field" : "name",
	"title" : "名称",
	"width" : 100,
	"align":"center",
	"sortable" : false
}, {
	"field" : "dataType",
	"title" : "部门/职务",
	"width" : 100,
	"align":"center",
	"sortable" : false
}, {
	"field" : "departmentContent",
	"title" : "描述",
	"width" : 100,
	"align":"center",
	"sortable" : false
}, {
	"field" : "flag",
	"title" : "是否启用",
	"width" : 100,
	"align":"center",
	"sortable" : false
}, {
	"field" : "_opt",
	"title" : "操作",
	"width" : 200,
	"align":"center",
	"formatter":function(value,row,index){   //格式化函数添加一个操作列
        var btn = '<input type="button" value="置顶" style="border:0" onclick="func_toFirst('+index+')">';
        btn=btn+' <input type="button" value="上移"  style="border:0" onclick="func_toPre('+index+')">';
        btn=btn+' <input type="button" value="下移"  style="border:0" onclick="func_toNext('+index+')">';
        btn=btn+' <input type="button" value="置底"  style="border:0" onclick="func_toBottom('+index+')">' ;
        return btn; 
       }
}
]]


/**
 * 行置顶
 */
function func_toFirst(index){
	var firstRow = $('#datagrid').datagrid('getData').rows[0];
    var currentRow = $('#datagrid').datagrid('getData').rows[index];
    $('#datagrid').datagrid('getData').rows[0] = currentRow;
    $('#datagrid').datagrid('getData').rows[index] = firstRow;
    $('#datagrid').datagrid('refreshRow', 0);
    $('#datagrid').datagrid('refreshRow', index);
    $('#datagrid').datagrid('unselectAll');
    $('#datagrid').datagrid('selectRow', 0);
    func_sort();
}
/**
 * 行上移
 */
function func_toPre(index){
    if(index>0){
	    var preRow = $('#datagrid').datagrid('getData').rows[index-1];
	    var currentRow = $('#datagrid').datagrid('getData').rows[index];
	    $('#datagrid').datagrid('getData').rows[index-1] = currentRow;
	    $('#datagrid').datagrid('getData').rows[index] = preRow;
	    $('#datagrid').datagrid('refreshRow', index-1);
	    $('#datagrid').datagrid('refreshRow', index);
	    $('#datagrid').datagrid('unselectAll');
	    $('#datagrid').datagrid('selectRow', index-1);
	    func_sort();
    }
}
/**
 * 行下移
 */
function func_toNext(index){
	var rows = $('#datagrid').datagrid('getRows').length;
	if (index != rows - 1) {
		   var nextRow = $('#datagrid').datagrid('getData').rows[index+1];
		    var currentRow = $('#datagrid').datagrid('getData').rows[index];
		    $('#datagrid').datagrid('getData').rows[index+1] = currentRow;
		    $('#datagrid').datagrid('getData').rows[index] = nextRow;
		    $('#datagrid').datagrid('refreshRow', index+1);
		    $('#datagrid').datagrid('refreshRow', index);
		    $('#datagrid').datagrid('unselectAll');
		    $('#datagrid').datagrid('selectRow', index+1);
		    func_sort();
	}
}
/**
 * 行置底
 */
function func_toBottom(index){
			var rows = $('#datagrid').datagrid('getRows').length;
		    var bottomRow = $('#datagrid').datagrid('getData').rows[rows-1];
		    var currentRow = $('#datagrid').datagrid('getData').rows[index];
		    $('#datagrid').datagrid('getData').rows[rows - 1] = currentRow;
		    $('#datagrid').datagrid('getData').rows[index] = bottomRow;
		    $('#datagrid').datagrid('refreshRow', rows-1);
		    $('#datagrid').datagrid('refreshRow', index);
		    $('#datagrid').datagrid('unselectAll');
		    $('#datagrid').datagrid('selectRow', rows-1);
		    func_sort();
}
/**
 * 排序
 */
function func_sort(){
	var rows = $('#datagrid').datagrid('getRows');
	var recordIds="";
	for(var i=0;i<rows.length;i++){
		recordIds=recordIds+rows[i].id+",";
	}
	$.ajax( {
		url : getContextPath() + "/tried_system/systemDepartmentAction_sort.action",
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
 * 部门信息列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/tried_system/systemDepartmentAction_list.action",
		collapsible : false,
		pagination : true,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		
		title:"<font color='red'> "+pName+" :</font>下属部门/职务",
		queryParams : { // 参数传递
			name : $("#name").textbox('getValue'),
			parentId:pId
		},
		columns : head

	});
}

function func_search(){
	$('#datagrid').datagrid('load', {
		name : $("#name").textbox('getValue'),
		parentId:pId
		});	
}

/**
 * 增加用户信息
 */
function func_add() {
	if(pId==0){	
		$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">请选择上级机构</font>', 'info');
		return;
	}
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '添加部门信息',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/SystemDepartment/manager.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			$('#modelForm').form('load', {pName:pName,parentId:pId});
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
					url : getContextPath() + "/tried_system/systemDepartmentAction_add.action",
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
		title : '编辑部门信息',
		width : 400,
		height : 300,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/SystemDepartment/manager.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},

		onLoad : function() {
			$('#modelForm').form('load', row);
			$('#modelForm').form('load', {pName:pName});
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
					url : getContextPath() + "/tried_system/systemDepartmentAction_edit.action",
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
 * 管理角色
 */
function func_role(){
	 
	var row = $('#datagrid').datagrid('getSelected');
	if (!row) {
		$.messager.alert('信息', '请先选择要操作的记录。', 'info');
		return;
	}
	var _depType= row.dataType;
	if(_depType!="职务"){
		$.messager.alert('信息', '角色关联必须选择数据类型为“职务”！。', 'info');
		return;
	}
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '管理 &nbsp;<font color="red">'+row.name+' </font> &nbsp;角色',
         width : 650,
         height : 380,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/system/SystemDepartment/depRole.html',
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },
         onLoad:function(){
        	 $("#depIdRole_edit").val(row.id)
        	    $.ajax({
					url : getContextPath() + "/tried_system/systemDepartmentAction_depGetRole.action",
					type : "post",
					dataType : "json",
				    async:false,  
					data :'id='+row.id,
			        success: function(DATA, request, settings){
			        	if(DATA.STATUS='SUCCESS'){
			        		var leftData=DATA.RETURN_DATA.leftMultiple;
			        		var rightData=DATA.RETURN_DATA.rightMultiple; 
				          for(var leftkey in leftData){ 
				             $('#leftRole').append("<option value='"+leftkey+"'>"+leftData[leftkey]+"</option>");   
				          }
				          for(var rightkey in rightData){ 
				             $('#rightRole').append("<option value='"+rightkey+"'>"+rightData[rightkey]+"</option>");   
				          }
			        	}
						return true;
			        },
			        error:function (event, request, settings){
                     	 return true;
			        }
		    	});
          },
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	  $.messager.progress({title:'请等待',msg:'保存数据中...'});
            	 	var roleid="";
	     	        $('#rightRole option').each(function(i) {
	     	   		   roleid=roleid+$(this).val()+",";
	             	}); 
            	 $.ajax({
 					url : getContextPath() + "/tried_system/systemDepartmentAction_depSetRole.action",
 					type : "post",
 					dataType : "json",
 				    async:true,  
 					data :'id='+row.id+'&recordIdS='+roleid,
 			        success: function(DATA, request, settings){
 			        	$.messager.progress('close');
 			        	if(DATA.STATUS='SUCCESS'){
 			        		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: blue">保存成功</font>','info');
 			        	}
 			        	else{
 			        		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: blue">保存失败</font>','info');
 			        	}
 						return true;
 			        },
 			        error:function (event, request, settings){
 			        	$.messager.progress('close');
                      	 return true;
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



function singleToRight(){
    $('#leftRole option:selected').each(function(i) {
  		$('#rightRole').append("<option value='"+$(this).val()+"'>"+ $(this).text()+"</option>");  
 }); 
 $('#leftRole').find("option:selected").remove();
  }
  //向左添加一个元素
  function singleToleft(){
    $('#rightRole option:selected').each(function(i) {
  		$('#leftRole').append("<option value='"+$(this).val()+"'>"+ $(this).text()+"</option>");  
 }); 
 $('#rightRole').find("option:selected").remove();
   }
  //全部移向右
  function batchToright(){
	 $('#leftRole option').each(function(i) {
  		$('#rightRole').append("<option value='"+$(this).val()+"'>"+ $(this).text()+"</option>");  
	}); 
  	$('#leftRole').empty();
  }
   //全部移向左
  function batchToleft(){
    $('#rightRole option').each(function(i) {
  		$('#leftRole').append("<option value='"+$(this).val()+"'>"+ $(this).text()+"</option>");  
	}); 
    $('#rightRole').empty();
   }
  
/**
 * 删除用户信息
 */
function func_del() {
	var rowId = "";
	var rows = $('#datagrid').datagrid('getSelections');
	for ( var i = 0; i < rows.length; i++) {
		rowId = rowId + rows[i].id + ',';
	}if(rowId==""){
		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要删除的数据</font>','warning');
		return;}
	$.messager.confirm('删除', '确定删除数据么?', function(r) {
		if (r) {
			$.messager.progress({
				title : '请等待',
				msg : '删除数据中...'
			});
			$.ajax({
				url : getContextPath() + "/tried_system/systemDepartmentAction_del.action",
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
	$('#treeDiv').layout({    
		height : _height
	});  
}
