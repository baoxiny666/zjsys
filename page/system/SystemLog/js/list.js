$(function() {
	reWindowSize();
	initDataGrid();
})
/**
 * 表头信息
 */
var head = [[{"field":"ck","checkbox":true}
,{
	"field":"logTime",
	"title":"日志时间",
	"width":200,
	"align":"center"
}
,{
	"field":"fileName",
	"title":"日志名称",
	"width":200,
	"align":"center"
},{
	"field":"_opt",
	"title":"操作",
	"width":200,
	"align":"center",
	"formatter":function(value,row,index){
		var btn = '<input type="button" value="下载" style="border:0" onclick="func_download(\''+row.pathName+'\',\''+row.fileName+'\')"> ';
		btn +='<input type="button" value="读取" style="border:0" onclick="func_readFile(\''+row.pathName+'\')">';
		return btn;
	}
}
]];
/**
 * 系统日志下载
 * 
 */
function func_download(filepath,filename){
	location.href=getContextPath() + '/tried_system/systemLoggerAction_downLoad.action?pathName=' + filepath+'&fileName='+filename;
}

function func_readFile(filePath){
	window.location.href = getContextPath()+"/page/system/SystemLog/loglist.html?filePath="+filePath+"&tempId="+getUrlParam("tempId");
}

/**
 * 系统日志表列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/tried_system/systemLoggerAction_list.action",
		collapsible : false,
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : {  //参数传递
			logTime : $("#log_time").datebox('getValue')
		},
		columns : head

	});
}

function func_search(){
	$('#datagrid').datagrid('load', {
		logTime : $("#log_time").datebox('getValue')
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
