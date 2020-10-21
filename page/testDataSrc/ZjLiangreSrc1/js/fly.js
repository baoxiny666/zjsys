/**
 * 表头信息
 */
var headFly = [ [ {
	"field" : "ck",
	"checkbox" : true
},{
	"field" : "sampleNum",
	"title" : "样品编号",
	"width" : "120",
	"align" : "center",
	"sortable" : false
},{
	"field" : "testDate",
	"title" : "分析日期",
	"width" : "120",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "test_ttfrValue",
	"title" : "弹筒发热量",
	"width" : "100",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "test_kgjgw",
	"title" : "空干基高热值",
	"width" : "140",
	"align" : "center",
	"sortable" : false
},  {
	"field" : "test_sdjdw",
	"title" : "收到基低位热值",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "dataStatus",
	"title" : "数据状态",
	"width" : "100",
	"align" : "center",
	"sortable" : false
},{
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
		url : getContextPath() + "/zjsys_testDataSrc/zjLiangreFlyAction_list.action",
		collapsible : false,
		pagination : true,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		sortOrder : 'desc',
		sortName : 'recordTime',
		queryParams : { // 参数传递
			sampleNum: $("#sampleNum_search").searchbox('getValue'),
			objStartTime: $("#objStartTime_search").datebox('getValue')
		},
		columns : headFly
	});
}

function func_search_fly() {
	$('#flygrid').datagrid('load', {
		sampleNum: $("#sampleNum_search").searchbox('getValue'),
		objStartTime: $("#objStartTime_search").datebox('getValue')
	});
}
