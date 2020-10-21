$(function() {
	reWindowSize();
	initDataGrid();
})
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
		rownumbers : true, //行号
		striped : true,    //各行换色
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : {  //参数传递
			//${searchName} : $("#${searchName}").val()
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
		//${searchName} : $("#${searchName}").val()
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
