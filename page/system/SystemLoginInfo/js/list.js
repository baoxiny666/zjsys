$(function() {
	reWindowSize();
	initDataGrid();
})
/**
 * 表头信息
 */
var head = [[{"field":"ck","checkbox":true},
             {"field":"loginName","title":"登录用户","width":120,"align":"center","sortable":false},
             {"field":"recordTime","title":"登录时间","width":150,"align":"center","sortable":false,
            	 "formatter":function(value,row,index){ if (value == undefined) { return ""; }else{ var unixTimestamp = new Date(value.time);  return unixTimestamp.toLocaleString();}}},
             {"field":"loginIP","title":"登录IP","width":100,"align":"center","sortable":false},
             {"field":"context","title":"描述","width":100,"align":"center","sortable":false}]];
/**
 * 用户登录记录表列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/tried_system/systemLoginInfoAction_list.action",
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : {  //参数传递
			loginName : $("#loginName_search").val()
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
		recordUserName : $("#recordUserName_search").val()
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
