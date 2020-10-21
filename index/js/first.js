$(function() {
	//initDBDataGrid();
//	initGgList();	
	//刷新待办列表
	//window.setInterval("func_dbSsearch()", 600000);
	   
});
 

var dbhead = [ [
        		{
        			"field" : "jbpm_title",
        			"title" : "标题",
        			"halign" : "center",
        			"align" : "left",
        			"sortable" : false,
        			"formatter" : function(value, row, index) {
        				if (row['jbpm_status'] == "已结束") {
        					return '<img style="border: 0" src="' + getContextPath() + '/img/icon/complete.png"/> ' + value;
        				} else {
        					return '<img style="border: 0" src="' + getContextPath() + '/img/icon/process.gif"/>' + value ;
        				}
        			}
        		}, {
        			"field" : "taskName",
        			"title" : "待办任务",
        			"width" : 110,
        			"align" : "center",
        			"sortable" : false
        		}, {
        			"field" : "task_createTime",
        			"title" : "接收时间",
        			"width" : 110,
        			"align" : "center",
        			"sortable" : false,
        			"formatter" : formateTime
        		}, {
        			"field" : "taskStatus",
        			"title" : "任务状态",
        			"width" : 60,
        			"align" : "center",
        			"sortable" : false,
        			"formatter" : function(value, row, index) {
        				if (value == "待处理") {
          					if (row['currentTaskStatus'] == "草稿") {
          						return '待发送';
          					} else {
          						if ("驳回" == row.currentTaskStatus) {
          							return '' + value + '<font style="color: red;font-weight: bold;">(' + row['currentTaskStatus'] + ')</font>';
          						} else {
          							return '<font style="color: red;font-weight: bold;">' + value + '</font>';
          						}
          					}
          				} else {
          					return '<font style="color: blue; ">' + value + '</font>';
          				}
          			}
        		}, 
        		 {
        			"field" : "jbpm_userName",
        			"title" : "发起人",
        			"width" : 80,
        			"align" : "center",
        			"sortable" : false
        		} 
        		, {
        			"field" : "jbpm_startTime",
        			"title" : "发起时间",
        			"width" : 120,
        			"align" : "center",
        			"sortable" : false,
        			"formatter" :formateTime
        		}, 
        		
        		{
        			"field" : "_flowLog",
        			"title" : "流程日志",
        			"width" : 40,
        			"align" : "center",
        			"sortable" : false,
        			"formatter" : function(value, row, index) {
        				var btn = '<input type="image" src="' + getContextPath() + '/img/png/flowlog.png" onclick="func_log(\'' + row.processId + '\')"  value="日志"/>';
        				return btn;
        			}
        		}, {
        			"field" : "_flow",
        			"title" : "流程跟踪",
        			"width" : 40,
        			"align" : "center",
        			"sortable" : false,
        			"formatter" : function(value, row, index) {
        				var btn = '<input type="image" src="' + getContextPath() + '/img/png/flow.png" onclick="func_flowImg(\'' + row.processId + '\')"  value="流程"/>';
        				return btn;
        			}
        		}, 
        		{
        			"field" : "_beizhu",
        			"title" : "流程备注",
        			"width" :60,
        			"align" : "center",
        			"sortable" : false,
        			"formatter" :function(value,row,index){ 
        				 var btn ='<input type="image" src="'+getContextPath()+'/css/themes/icons/icon_beizhu.png" onclick="func_AddModelContext(\''+row.processId+'\')"  />';
        				return btn;
        			}
        		} , {
        			"field" : "_opt",
        			"title" : "操作",
        			"width" : 60,
        			"align" : "center",
        			"sortable" : false,
        			"formatter" : function(value, row, index) {
        				var btn = '<input type="button"  onclick="func_check(\'' + row.taskId + '\',\'' + row.taskName + '\',\'' + row.taskForm + '\')"  value="处理"/>';
        				return btn;
        			}
        		} ] ];
 
/**
 * 公告列表
 */
function initGgList() {
	$.ajax({
		url : getContextPath() + "/tried_system/systemNoticeAction_part_list.action?rows=10",
		type : "post",
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
			$.messager.progress('close');
			$.each(DATA, function(k, v) {
				var _title=v.notice_title;
				if(v.notice_title.length>15){
					_title=v.notice_title.substr(0,15)+"...";
				}
				var _tr = '<tr valign="top"><td height="15px"   style="text-align: left; border: 0px; border-bottom: 1px solid #DBDBDB;padding-left: 3px">'
						+ '<a href="#" onclick="func_gg_info(\'' + v.id + '\')" style="text-decoration: none;">' +_title+ '</a></td><td style="text-align: right; border: 0px; border-bottom: 1px solid #DBDBDB"">【' + formateDate(v.recordTime) + '】</td>'
						+ '</tr>';
				$("#gg_id").append(_tr);
			});
		},
		error : function(event, request, settings) {
			relogin();
			func_msg_error("网络异常!");
		}
	});

}
/**
 * 公告详情
 */
function func_gg_info(id) {		
	$('<div></div>').dialog({
		id : 'modelWindow1',
		width : 1000,
		height :450,
		title : '公告信息',
		closed : false,
		cache : false,
		href : getContextPath() + '/page/system/SystemNotice/info.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			func_initForm(id);
		},
		buttons : [ {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$("#modelWindow1").dialog('destroy');
			}
		} ]
	});
}


/**
 * 首页待办处理
 * @param processId
 * @param taskId
 * @param taskName
 */
function func_check(taskId,taskName,taskForm){
var _url=getContextPath() + taskForm;
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : taskName,
		width : 1000,
		height : 450,
		closed : false,
		cache : false,
		href : _url,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			func_initForm(taskId);
		},
		buttons : [ {
			text : '保存',
			iconCls : 'icon-ok',
			handler : function() {
				$.messager.progress({
					title : '请等待',
					msg : '数据处理中...'
				});
				$('#modelForm').form('submit', {
					url : getContextPath() + $("#modelForm").attr("action"),
					onSubmit : function() {
						var isValid = $(this).form('enableValidation').form('validate');
						if (!isValid) {
							$.messager.progress('close');
						}
						return isValid;
					},
					success : function(data) {
						var resultData = jQuery.parseJSON(data);
						$.messager.progress('close');
						$("#modelWindow").dialog('destroy');
						if (resultData.STATUS == "SUCCESS") {
							func_msg_info(resultData.RETURN_DATA);
							func_dbSsearch();
							//如果有弹框则同时更新弹框
							func_initReloadTaskMessage();
						} else {
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
		} ],
	});
}

//公告更多
function funcGgMore(){
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : "公告",
		width : 1100,
		height : 450,
		closed : false,
		cache : false,
		href : getContextPath() + "/page/system/SystemNotice/more.html",
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			
			func_load_gg();
		},
		buttons : [  {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$("#modelWindow").dialog('destroy');
			}
		} ],
	});
 
}

/**
 *待办事项、已办事项
 */

function initDBDataGrid() {
	$('#db-datagrid').datagrid({
		url : getContextPath() + "/system_jbpm/viewJbpmTaskDbAction_firstDb.action",
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		singleSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		fitColumns : true,
		columns : dbhead,
		onLoadSuccess:function(data){		
			initColumn();//初始化柱状图			
		} 
	});
}
function func_dbSsearch() {
	$('#db-datagrid').datagrid('reload');
}
/**
 * 初始化柱状图
 */
function initColumn(){
	var xdata=[];
	var db_data=[];
	var yb_data=[];
	 $.ajax({
			url :  getContextPath() + "/system_jbpm/viewJbpmTaskDbAction_numYbDb.action",				
			type : "post",
			dataType : "json",
			async : false,
			success : function(DATA, request, settings) {
				var map={};
				var _data = DATA.RETURN_DATA;
				 
				$.each(_data.processName,function(i,v){
					 xdata.push(v);
					 if(_data.ybData[v]!=undefined){
						 yb_data.push(_data.ybData[v]);
					 }else{
						 yb_data.push(0);
					 }
					 if(_data.dbData[v]!=undefined){
						 db_data.push(_data.dbData[v]);
					 }else{
						 db_data.push(0);
					 }
					
				});
				 	
			},
			error : function(event, request, settings) {
				relogin();
				func_msg_error("网络异常!");
			}
		});
	
	
	
	$('#db_container').highcharts({
		chart: {
			type: 'column'
		},
		title: {
			text: '个人任务分布'
		},
		credits : {
			enabled : false
		},
		xAxis: {
			categories: xdata,
			crosshair: true
		},
		yAxis: {
			min: 0,
			title: {
				text: '数量 (个)'
			},
			allowDecimals:false
		},
		tooltip: {
			// head + 每个 point + footer 拼接成完整的 table
			headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			'<td style="padding:0"><b>{point.y:.0f} 个</b></td></tr>',
			footerFormat: '</table>',
			shared: true,
			useHTML: true
		},
		plotOptions: {
			column: {
				borderWidth: 0
			}
		},
		series: [{
			name: '待办',
			color:'#55BF3B',
			data:db_data
		}, {
			name: '已办',
			color:'#7798BF',
			data:yb_data
		}]
	   });
}

/**
 * 页面缩放监听事件
 */
$(window).resize(function() {
}).resize();
/**
 * 页面缩放自适应大小
 */

