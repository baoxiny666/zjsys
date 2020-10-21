/**
 * 表头信息
 */
var headFly = [ [ {
	"field" : "ck",
	"checkbox" : true
}, {
	"field" : "deviceNum",
	"title" : "设备编号",
	"width" : "140",
	"align" : "center",
	"sortable" : false
},  {
	"field" : "sample_no",
	"title" : "样品编号",
	"width" : "120",
	"align" : "center",
	"sortable" : false
},{
	"field" : "tesDate",
	"title" : "分析日期",
	"width" : "100",
	"align" : "center",
	"sortable" : false,
	"formatter" :function(value,  row, index){
		return value.substr(0,10);
	}
}, {
	"field" : "stad_value",
	"title" : "Stad",
	"width" : "100",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "dataStatus",
	"title" : "数据状态",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "recordTime",
	"title" : "提交时间",
	"width" : "140",
	"align" : "center",
	"sortable" : false,
	"formatter" : formateDate
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
		url : getContextPath() + "/zjsys_testDataSrc/zjDingliuFlyAction_list.action",
		collapsible : false,
		pagination : true,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		sortOrder : 'desc',
		sortName : 'tesDate',
		queryParams : { // 参数传递
			objStartTime: $("#objStartTime_search").datebox('getValue'),
			deviceNum : 'YX-DLA8500',
			sample_no: $("#sampleNum_search").textbox('getValue'),
			dataStatus : '已发送'
		},
		columns : headFly

	});
	
}

function func_search_fly() {
	$('#flygrid').datagrid('load', {
		objStartTime: $("#objStartTime_search").datebox('getValue'),
		deviceNum : 'YX-DLA8500',
		sample_no: $("#sampleNum_search").textbox('getValue'),
		dataStatus : '已发送'
	});
}
