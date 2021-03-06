//父节点id
var pId=0;
//父节点名称
var pName="";
$(function() {
	initTree();
	reWindowSize();
	initDataGrid();
})

/**
 * 初始化${modelTitle}结构树
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
	

	var zNodes =new Array();
		$.ajax({
			url : getContextPath() + "/${systemName}_${packName}/${modelName}Action_initTree.action",
			type : "post",
			dataType : "json",
			async : false,
			success : function(DATA, request, settings) {
				if(DATA.STATUS=='SUCCESS'){
					zNodes=DATA.RETURN_DATA;
					 zNodes.push({ id:1, pId:0, name:"树 ", open:true})
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
	$("#${searchName}").val("");
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
		url : getContextPath() + "/${systemName}_${packName}/${modelName}Action_sort.action",
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
		url : getContextPath() + "/${systemName}_${packName}/${modelName}Action_drop.action",
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
var head = [${headTitle}];
/**
 * ${modelTitle}列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/${systemName}_${packName}/${modelName}Action_list.action",
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		title:"<font color='red'> "+pName+" :</font>下属功能",
		queryParams : {  //参数传递
			
			${searchName} : $("#${searchName}").val(),
			parentId:pId
		},
		columns : head

	});
}

/**
 * 检索
 * @param gridId
 */
function func_search(){
	$('#datagrid').datagrid('load',{
		${searchName} : $("#${searchName}").val(),
		parentId:pId
	}); 
}
/**
 * 增加${modelTitle}信息
 */
function func_add() {
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '添加 &nbsp;<font color="red">${modelTitle}</font> ',
         width : 400,
         height : 300,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/${packName}/${ModelName}/manager.html',
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
            	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
    				$('#modelForm').form('submit', {
    					url: getContextPath() + "/${systemName}_${packName}/${modelName}Action_add.action",
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
    							func_search();
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
 * 编辑${modelTitle}信息
 */
function func_edit(){
	var row = $('#datagrid').datagrid('getSelected');
	if(!row){
		$.messager.alert('信息','请先选择要更新的记录。','info'); 
		return;
	}
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '编辑 &nbsp;<font color="red">${modelTitle}</font> ',
         width : 400,
         height : 300,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/${packName}/${ModelName}/manager.html',
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
    					url: getContextPath() + "/${systemName}_${packName}/${modelName}Action_edit.action",
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
    							func_search();
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
 * 删除${modelTitle}信息
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
			$.ajax( {
				url : getContextPath() + "/${systemName}_${packName}/${modelName}Action_del.action",
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
	$('#treeDiv').layout({    
		height : _height
	});  
}
