var tempId = "";
$(function() {
	tempId = getUrlParam("tempId");
	reWindowSize();
	initDataGrid();
})
/**
 * 表头信息
 */
var head = [[{
	"field":"logTime",
	"title":"时间",
	"align":"center"
},{
	"field":"content",
	"title":"日志内容",
	"width":500,
	"align":"center"
}
]];

/**
 * 系统日志表列表初始化
 */
function initDataGrid() {
	var filePath = getUrlParam("filePath");
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/tried_system/systemLoggerAction_readFile.action",
		collapsible : false,
		//pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : {  //参数传递
			pathName : filePath
		},
		columns : head
	});
}
/**
 * 返回
 */
function func_undo(){
	window.location.href = getContextPath()+"/page/system/SystemLog/list.html?tempId="+tempId;
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
