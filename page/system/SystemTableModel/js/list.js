$(function() {
	initTree();
	reWindowSize();
	initDataGrid();
})
//父节点id
var pId=0;
//父节点名称
var pName="";
var pTable="";
/**
 * 初始化表/视图结构结构树
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
	
	var zNodes =[{ id:1, pId:0, name:"树 ", open:true}];
		$.ajax({
			url : getContextPath() + "/tried_system/systemTableModelAction_initTree.action",
			type : "post",
			dataType : "json",
			async : false,
			success : function(DATA, request, settings) {
				if(DATA.STATUS=='SUCCESS'){
					zNodes=DATA.RETURN_DATA;
				}
			},
			error : function(event, request, settings) {
				func_msg_error("网络异常!");
			}
		});
		$.fn.zTree.init($("#menuTree"), setting, zNodes);
}
/**
 * 单击事件
 * @param event
 * @param treeId
 * @param treeNode
 * @param clickFlag
 */
function reSetContent(event, treeId, treeNode, clickFlag) {	
	pId=treeNode.id;
	pName=treeNode.name;
	pTable=treeNode.objectTable;
	initDataGrid() ;
}

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
		url : getContextPath() + "/tried_system/systemTableModelAction_sort.action",
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
			func_msg_error("网络异常!");
		}
	});
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
		url : getContextPath() + "/tried_system/systemTableModelAction_drop.action",
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

/**
 * 构列表初始化
 */
function initDataGrid() {
	var head = [ [ {
		"field" : "ck",
		"checkbox" : true
	} , {
		"field" : "objectName",
		"title" : "TABLE标题",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	}, {
		"field" : "objectTable",
		"title" : "TABLE名称",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	}, {
		"field" : "columnName",
		"title" : "字段名称",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	}, {
		"field" : "columnTitle",
		"title" : "字段标题",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	}, 
	 {
		"field" : "columnType",
		"title" : "字段类型",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	}, 
	{
		"field" : "columnLength",
		"title" : "字段长度",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	}, 
	{
		"field" : "columnIsNull",
		"title" : "是否可空",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	},{
		"field" : "columnSearch",
		"title" : "是否搜索条件",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	}, {
		"field" : "columnSort",
		"title" : "是否排序",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	},{
		"field" : "recordUser",
		"title" : "执行人",
		"width" : 100,
		"align" : "center",
		"sortable" : false
	},{
		"field" : "recordDate",
		"title" : "执行时间",
		"width" : 150,
		"align" : "center",
		"sortable" : false,
		"formatter": formateTime

		
	}, {
		"field" : "_opt",
		"title" : "操作",
		"width" : 200,
		"align" : "center",
		"formatter" : function(value, row, index) {
			var btn = '<input type="button" value="置顶" style="border:0" onclick="func_toFirst(' + index + ')">';
			btn = btn + ' <input type="button" value="上移"  style="border:0" onclick="func_toPre(' + index + ')">';
			btn = btn + ' <input type="button" value="下移"  style="border:0" onclick="func_toNext(' + index + ')">';
			btn = btn + ' <input type="button" value="置底"  style="border:0" onclick="func_toBottom(' + index + ')">';
			return btn;
		}
	} ] ];
	if(pId==0){
		 head = [ [ {
			"field" : "ck",
			"checkbox" : true
		}  , {
			"field" : "objectTable",
			"title" : "表名称",
			"width" : 100,
			"align" : "center",
			"sortable" : false
		},{
			"field" : "objectName",
			"title" : "表描述",
			"width" : 100,
			"align" : "center",
			"sortable" : false
		},
		{
			"field" : "packName",
			"title" : "归属模块",
			"width" : 100,
			"align" : "center",
			"sortable" : false
		},{
			"field" : "recordUser",
			"title" : "执行人",
			"width" : 100,
			"align" : "center",
			"sortable" : false
		},{
			"field" : "recordDate",
			"title" : "执行时间",
			"width" : 150,
			"align" : "center",
			"sortable" : false,
			"formatter": formateTime

		}] ];
	}
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/tried_system/systemTableModelAction_list.action",
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		title:"<font color='red'> "+pName+" :</font>下属字段",
		queryParams : {  //参数传递
			parentId : pId
		},
		columns : head

	});
}

function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {

        dt = new Date(value);

    }

    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
}
/**
 * 增加用户信息
 */
function func_add() {
	
	
	var addUrl= getContextPath() + '/page/system/SystemTableModel/column.html';
	var _height = 350;
	if(pId==0){
		addUrl= getContextPath() + '/page/system/SystemTableModel/table.html';
		_height = 200;
	}
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '添加 &nbsp;<font color="red">表/视图结构</font> ',
         width : 400,
         height : _height,
         closed : false,
         cache : false,
         href : addUrl,
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },
         onLoad : function() {
        	if(pId==0){
        		$('#modelForm').form('load', {parentId:pId});
        		}
 			$('#modelForm').form('load', {objectTable:pTable ,objectName:pName,parentId:pId});
 		},
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
    				$('#modelForm').form('submit', {
    					url: getContextPath() + "/tried_system/systemTableModelAction_add.action",
    					onSubmit:function(){
    						var isValid = $(this).form('enableValidation').form('validate');
    						if (!isValid){
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
    							initTree();
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
 * 编辑模型信息
 */
function func_edit(){
	var row = $('#datagrid').datagrid('getSelected');
	if(!row){
		$.messager.alert('信息','请先选择要更新的记录。','info'); 
		return;
	}
	
	var _height = 350;
	var addUrl= getContextPath() + '/page/system/SystemTableModel/column.html';
	if(pId==0){
		var _height = 200;
		addUrl= getContextPath() + '/page/system/SystemTableModel/table.html';
	}
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '编辑 &nbsp;<font color="red">表结构</font> ',
         width : 400,
         height : _height,
         closed : false,
         cache : false,
         href : addUrl,
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },
         
         onLoad:function(){
        	 $('#modelForm').form('load',row);
        	 $('#modelForm').form('load', {pName:pName});
          },
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
    				$('#modelForm').form('submit', {
    					url: getContextPath() + "/tried_system/systemTableModelAction_edit.action",
    					onSubmit:function(){
    						var isValid = $(this).form('enableValidation').form('validate');
    						if (!isValid){
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
    							initTree();
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
 * 删除模型信息
 */
function func_del(){
    var rowId="";
	var rows = $('#datagrid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		rowId=rowId+rows[i].id+',';
	}
	if(rowId==""){
		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要删除的数据</font>','warning');
	return;}
	$.messager.confirm('删除', '确定删除数据么?', function(r){
		if (r){
			  $.messager.progress({title:'请等待',msg:'删除数据中...'});
			$.ajax({
				url : getContextPath() + "/tried_system/systemTableModelAction_del.action",
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
 * 模型创建
 */
function func_creat(){
	if(pId!=0){
		alert("请选择表");return;
	}
	    var rowId="";
		var rows = $('#datagrid').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			rowId=rowId+rows[i].id+',';
		}
		if(rowId==""){
			$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要生成的的表</font>','warning');
			return;
		}
		$('<div></div>').dialog({
	         id : 'modelWindow',
	         title : '生成 &nbsp;<font color="red">对象模型</font> ',
	         width : 400,
	         height : 150,
	         closed : false,
	         cache : false,
	         href : getContextPath() + '/page/system/SystemTableModel/create.html',
	         modal : true,
	         onClose : function() {
	             $(this).dialog('destroy');
	         },
	         onLoad:function(){
	        	 $('#modelForm').form('load', {recordIdS:rowId});
	          },
	         buttons : [ {
	             text : '执行',
	             iconCls : 'icon-ok',
	             handler : function() {
	            	 $.messager.progress({title:'请等待',msg:'执行数据中...'});
	    				$('#modelForm').form('submit', {
	    					url: getContextPath() + "/tried_system/systemTableModelAction_createModel.action",
	    					onSubmit:function(){
	    						var isValid = $(this).form('enableValidation').form('validate');
	    						if (!isValid){
	    							$.messager.progress('close');
	    						}
	    						return isValid;	
	    					},
	    					success: function(){
	    						$.messager.progress('close');	
	    					    $("#modelWindow").dialog('destroy');
	    					    $.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: blue">执行完毕</font>','info');
	    					     initDataGrid();
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




 