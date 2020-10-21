/**
 * 表头信息
 */
var headFly = [ [ {
	"field" : "ck",
	"checkbox" : true
}, {
	"field" : "deviceNum",
	"title" : "设备编号",
	"width" : "100",
	"align" : "center",
	"sortable" : false
},{
	"field" : "sampleNum",
	"title" : "样品编号",
	"width" : "120",
	"align" : "center",
	"sortable" : true
}, {
	"field" : "resultc",
	"title" : "C",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "results",
	"title" : "S",
	"width" : "140",
	"align" : "center",
	"formatter" :formate3num,
	"sortable" : false
}, {
	"field" : "dataStatus",
	"title" : "数据状态",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "time",
	"title" : "分析时间",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "recordTime",
	"title" : "提交时间",
	"width" : "140",
	"align" : "center",
	"sortable" : false,
	"formatter" : formateTime
}, {
	"field" : "recordUserName",
	"title" : "提交人",
	"width" : 100,
	"align" : "center",
	"sortable" : false
} ] ];
/**
 * -列表初始化
 */
function initFlyGrid() {
	$('#flygrid').datagrid({
		toolbar : '#tbEXC',
		url : getContextPath() + "/zjsys_testDataSrc/zjTanliuFlyAction_list.action",
		collapsible : false,
		pagination : true,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		sortOrder : 'asc',
		sortName : 'time',
		queryParams : { // 参数传递
			sampleNum: $("#sampleNum_search").textbox('getValue'),
			objStartTime: $("#objStartTime_search").datebox('getValue'),
			deviceNum : 'ZJ-ZX-029',
			dataStatus : '已发送'
		},
		columns : headFly

	});
}

function func_search_fly() {
	$('#flygrid').datagrid('load', {
		sampleNum: $("#sampleNum_search").textbox('getValue'),
		objStartTime: $("#objStartTime_search").datebox('getValue'),
		deviceNum : 'ZJ-ZX-029',
		dataStatus : '已发送'
	});
}
