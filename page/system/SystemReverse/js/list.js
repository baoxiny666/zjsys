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
	"field" : "tabel_type",
	"title" : "对象类型",
	"width" : 100,
	"align" : "center",
	"sortable" : false
}, {
	"field" : "table_name",
	"title" : "对象名字",
	"width" : 200,
	"align" : "center",
	"sortable" : false
}, {
	"field" : "tabel_content",
	"title" : "对象描述",
	"width" : 200,
	"align" : "center",
	"sortable" : false
} ] ];
/**
 * 逆向工具列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/tried_system/systemReverseAction_list.action",
		collapsible : false,
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : { // 参数传递
			table_name : $("#table_name").val()
		},
		columns : head

	});
}
/**
 * 模型创建
 */
function func_creat(){
	
	    var rowId="";
		var rows = $('#datagrid').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			rowId=rowId+rows[i].table_name+',';
		}
		if(rowId==""){
			$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要生成的的对象</font>','warning');
			return;
		}
		$('<div></div>').dialog({
	         id : 'modelWindow',
	         title : '生成 &nbsp;<font color="red">对象模型</font> ',
	         width : 400,
	         height : 150,
	         closed : false,
	         cache : false,
	         href : getContextPath() + '/page/system/SystemReverse/create.html',
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
	    					url: getContextPath() + "/tried_system/systemReverseAction_createModel.action",
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
