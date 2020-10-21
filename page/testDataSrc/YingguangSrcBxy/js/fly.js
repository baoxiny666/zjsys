
/**
 * 表头信息
 */
var headFly = [[
             {"field":"ck","checkbox":true},   
             {"field":"dataTime","title":"分析日期","width":"120","align":"center","sortable":false},
             {"field":"sampleNum","title":"样品编号","width":"120","align":"center","sortable":false},                               
             {"field":"analyElement","title":"分析物","width":"120","align":"center","sortable":false},
             {"field":"analyResult","title":"结果","width":"140","align":"center","sortable":false},           
             {"field":"recordTime","title":"操作时间","width":"140","align":"center","sortable":false,"formatter":formateDate},
             {"field":"recordUserName","title":"操作人","width":100,"align":"center","sortable":false}]];
/**
 * -列表初始化
 */
function initFlyGrid() {
	$('#flygrid').datagrid({
		toolbar : '#tbEXC',
		url : getContextPath() + "/zjsys_testDataSrc/yingguangFlyBxyAction_list.action",
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		sortOrder:'desc',
		sortName:'dataTime',	 
		queryParams : {  //参数传递
			objStartTime: $("#objStartTime_search").datebox('getValue'),
			deviceNum : GDDeviceNum,
			dataStatus:'已提交'
		},
		columns : head

	});
}

function func_search_fly(){
	$('#flygrid').datagrid('load',{
		objStartTime: $("#objStartTime_search").datebox('getValue'),
		deviceNum : GDDeviceNum,
		dataStatus:'已提交'
	}); 
}

 