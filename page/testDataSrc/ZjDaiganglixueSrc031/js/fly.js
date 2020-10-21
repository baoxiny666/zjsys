
/**
 * 表头信息
 */
var headFly = [[{"field":"ck","checkbox":true},          
               /* {"field":"deviceNum","title":"设备编号","width":"140","align":"center","sortable":false},
                {"field":"filename","title":"文件名","width":"140","align":"center","sortable":false},*/
                {"field":"testTime","title":"分析日期","width":"120","align":"center","sortable":false},
                {"field":"lunum","title":"炉号","width":"100","align":"center","sortable":false},
                {"field":"steeltype","title":"钢种","width":"100","align":"center","sortable":false},
                {"field":"steelGuige","title":"规格","width":"100","align":"center","sortable":false},
                {"field":"branchFactory","title":"生产线","width":"100","align":"center","sortable":false},
                {"field":"yieldDown_streng","title":"下屈服强度(MPa)","width":"140","align":"center","sortable":false},
                {"field":"kangla_streng","title":"抗拉强度(MPa)","width":"140","align":"center","sortable":false},
                {"field":"duanhouLong_rate","title":"断后伸长率(%)","width":"140","align":"center","sortable":false},
                {"field":"recordTime","title":"操作时间","width":"140","align":"center","sortable":false,"formatter":formateDate},
                {"field":"recordUserName","title":"操作人","width":100,"align":"center","sortable":false}
                ]];
/**
 * -列表初始化
 */
function initFlyGrid() {
	$('#flygrid').datagrid({
		toolbar : '#tbEXC',
		url : getContextPath() + "/zjsys_testDataSrc/zjDaiganglixueFlyAction_list.action",	
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		//sortOrder:'desc',
		//sortName:'filename',	
		queryParams : {  //参数传递
			deviceNum :GDdeviceNum,
			objStartTime:$("#objStartTime_search").datebox("getValue"),
			dataStatus:'已发送',
			
			classGroup:$("#classGroup_search").textbox("getValue"),
			lunum:$("#lunum_search").textbox("getValue"),
			steeltype:$("#steeltype_search").textbox("getValue"),
			steelGuige:$("#steelGuige_search").textbox("getValue"),
			branchFactory:$("#branchFactory_search").textbox("getValue")
			
			
			
		},
		columns : headFly

	});
}

function func_search_fly(){
	$('#flygrid').datagrid('load',{
		deviceNum : GDdeviceNum,
		objStartTime:$("#objStartTime_search").datebox("getValue"),
		dataStatus:'已发送',
		classGroup:$("#classGroup_search").textbox("getValue"),
		lunum:$("#lunum_search").textbox("getValue"),
		steeltype:$("#steeltype_search").textbox("getValue"),
		steelGuige:$("#steelGuige_search").textbox("getValue"),
		branchFactory:$("#branchFactory_search").textbox("getValue")
	}); 
}

 